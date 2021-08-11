package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.AstBaseVisitor;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration.AstFunctionArgs;
import ca.applin.selmer.lang.ast.AstNumLitteral;
import ca.applin.selmer.lang.ast.AstReturnExpression;
import ca.applin.selmer.lang.ast.AstStringLitteral;
import ca.applin.selmer.lang.ast.AstVariableAssignement;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.DoubleType;
import ca.applin.selmer.lang.ast.type.IntType;
import ca.applin.selmer.lang.scope.Scope;
import java.util.List;
import java.util.function.Function;

public class Typer extends AstBaseVisitor<AstType> {

    private final ReturnStatementFinder finder = new ReturnStatementFinder();

    private Scope scope;

    public Typer(Scope scope) {
        this.scope = scope;
    }

    private void pushScope(Scope scope) {
        scope.parent = this.scope;
        this.scope = scope;
    }

    private void popScope() {
        this.scope = this.scope.parent;
    }

    @Override
    public AstType visit(AstStringLitteral astStringLitteral) {
        return astStringLitteral.type;
    }

    @Override
    public AstType visit(AstNumLitteral astNumLitteral) {
        return astNumLitteral.type;
    }

    @Override
    public AstType visit(AstBinop binop) {
        if (binop.type.isKnown()) {
            return binop.type;
        }
        AstType left = binop.left.visit(this);
        AstType right = binop.right.visit(this);
        if (left.equals(right)) {
            binop.type = left;
            return binop.type;
        }
        if (intAndDoubleMismatch(left, right)) {
            binop.type = DoubleType.INSTANCE;
            return binop.type;
        }
        throw new MismatchTypeException("Binop '%s' operands types left:%s and right:%s are different"
            .formatted(binop.operator, left.toString(), right.toString()));
    }

    private boolean intAndDoubleMismatch(AstType left, AstType right) {
        return (left.equals(DoubleType.INSTANCE) && right.equals(IntType.INSTANCE))
            || (left.equals(IntType.INSTANCE) && right.equals(DoubleType.INSTANCE));
    }

    // todo
    @Override
    public AstType visit(AstFunctionDeclaration funcDecl) {
        if (scope.isAlreadyKnown(funcDecl.name)) {
            // @Error better error reporting with pos and code
            throw new RuntimeException("Function %s already declared."
                    .formatted(funcDecl.name));
        }

        if (scope.knownFunc.containsKey(funcDecl.name) && allTypeResolved(funcDecl)) {
            return funcDecl.type;
        }

        // @Improvement visit body only once for all args?
        funcDecl.args.forEach(arg -> tryInferArg(arg, funcDecl.body));

        // infer return type
        AstType inferedReturnType = funcDecl.body.code.size() == 1
                ? inferSingleExpressionFunctionReturnType(funcDecl)
                : inferFunctionReturnType(funcDecl);

        scope.knownFunc.put(funcDecl.name, funcDecl);
        return null;
    }

    private void tryInferArg(AstFunctionArgs arg, AstCodeBlock body) {

    }

    private boolean allTypeResolved(AstFunctionDeclaration funcDecl) {
        if (funcDecl.type.isKnown())  return true;
        boolean allArgsKnown = funcDecl.args.stream()
                .map(AstFunctionArgs::type)
                .allMatch(AstType::isKnown);
        if (!allArgsKnown) return false;


    }


    private AstType inferFunctionReturnType(AstFunctionDeclaration astFunctionDeclaration) {
        // find all return statement, make sure they are of the same type, return that type
        List<AstReturnExpression> returnsExpr = finder.visit(astFunctionDeclaration);
        return null;
    }


    private AstType inferSingleExpressionFunctionReturnType(AstFunctionDeclaration astFunc) {
        assert astFunc.body.code.size() == 1
                : "Function %s must have only a single expression to infer type, but was found to have %d"
                    .formatted(astFunc.name, astFunc.body.code.size());
        AstExpression expr = (AstExpression) astFunc.body.code.get(0);
        return expr.visit(this);
    }

    @Override
    public AstType visit(AstFuncCall astFuncCall) {
        if (astFuncCall.type.isKnown()) {
            return astFuncCall.type;
        }
        return astFuncCall.base.visit(this);
    }

    @Override
    public AstType visit(AstCodeBlock codeBlock) {
        pushScope(new Scope());
        codeBlock.code.forEach(cb -> cb.visit(this));
        popScope();
        return AstType.NO_TYPE;
    }

    @Override
    public AstType visit(AstVariableDeclaration astVariableDeclaration) {
        // if type is declared and it has an init, we nee to check if type matches
        if (scope.isAlreadyKnown(astVariableDeclaration.varName)) {
            // @Error better error reporting with pos and code
            throw new RuntimeException("Variable %s already declared."
                    .formatted(astVariableDeclaration.varName));
        }

        AstType typeDeclared = astVariableDeclaration.type;
        AstExpression initExpr = astVariableDeclaration.initExpr;
        if (initExpr == null && !typeDeclared.isKnown()) {
            throw new RuntimeException("[SHOULD NEVER HAPPEND] Variable %s has no init expr and type is unknown");
        }

        // case x : Int = 5
        if (initExpr != null && typeDeclared.isKnown()) {
            AstType initExprInferedType = initExpr.visit(this);
            if (!typeDeclared.equals(initExprInferedType)) {
                // @Error better error reporting with pos and code
                throw new MismatchTypeException(
                        "%s\nVariable declaration %s was declared with [%s] but was infered to [%s] by expression %s"
                        .formatted(astVariableDeclaration.toString(), astVariableDeclaration.varName, typeDeclared, initExprInferedType, initExpr.toString())
                );
            }
            astVariableDeclaration.type = initExprInferedType;
            scope.knownVariables.put(astVariableDeclaration.varName, astVariableDeclaration);
            return initExprInferedType;
        }

        // case x : Int
        if (initExpr == null) {
            return typeDeclared;
        }

        // case c := 1 + 2
        AstType initExprInferedType = initExpr.visit(this);
        astVariableDeclaration.type = initExprInferedType;
        scope.knownVariables.put(astVariableDeclaration.varName, astVariableDeclaration);
        return initExprInferedType;
    }

    @Override
    public AstType visit(AstVariableAssignement assign) {
        // @Error better error reporting with pos and code
        AstVariableDeclaration decl = scope.getVarByNameOrThrow(assign.varName,
            new RuntimeException("Variable %s not declared, cannot assign value %s to it."
                    .formatted(assign.varName, assign.expr.toString())));
        AstType varType = decl.type;
        AstType exprType = assign.expr.visit(this);
        if (!varType.equals(exprType)) {
            // @Error better error reporting with pos and code
            throw new MismatchTypeException("Cannot assign value %s of type %s to variable %s of type %s"
                .formatted(assign.expr.toString(),
                        exprType, assign.varName, varType));
        }
        decl.type = exprType;
        return super.visit(assign);
    }

    @Override
    public AstType visit(AstVariableReference ref) {
        // @Error better error reporting with pos and code
        AstVariableDeclaration decl = scope.getVarByNameOrThrow(ref.varName,
                new RuntimeException("Cannot find reference for variable %s".formatted(ref.varName)));
        AstType typeKnown = scope.findTypeFor(ref.varName);
        if (typeKnown.isKnown()) {
            ref.type = typeKnown;
            return ref.type;
        }
        return decl.type;
    }
}
