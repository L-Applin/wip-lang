package ca.applin.selmer.lang.typer;

import static java.util.stream.Collectors.joining;

import ca.applin.selmer.lang.AstBaseVisitor;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration.AstFunctionArgs;
import ca.applin.selmer.lang.ast.AstLambdaExpression;
import ca.applin.selmer.lang.ast.AstLambdaExpression.AstLambdaArgs;
import ca.applin.selmer.lang.ast.AstNumLitteral;
import ca.applin.selmer.lang.ast.AstReturnExpression;
import ca.applin.selmer.lang.ast.AstStringLitteral;
import ca.applin.selmer.lang.ast.AstVariableAssignement;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.AstTypeFunction;
import ca.applin.selmer.lang.ast.type.DoubleType;
import ca.applin.selmer.lang.ast.type.IntType;
import ca.applin.selmer.lang.scope.Scope;
import java.util.List;

public class Typer extends AstBaseVisitor<AstType> {

    private final ReturnStatementFinder finder = new ReturnStatementFinder();

//    private Scope scope;
//    private AstVariableDeclaration source;

//    public Typer(Scope scope) {
//        this.scope = scope;
//    }

//    private void pushScope(Scope scope) {
//        scope.parent = this.scope;
//        this.scope = scope;
//    }
//
//    private void popScope() {
//        this.scope = this.scope.parent;
//    }

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
            .formatted(binop.operator, left.toString(), right.toString()), binop);
    }

    private boolean intAndDoubleMismatch(AstType left, AstType right) {
        return (left.equals(DoubleType.INSTANCE) && right.equals(IntType.INSTANCE))
            || (left.equals(IntType.INSTANCE) && right.equals(DoubleType.INSTANCE));
    }

    // todo
    @Override
    public AstType visit(AstFunctionDeclaration funcDecl) {
        Scope scope = funcDecl.scope;
        if (scope.isAlreadyKnown(funcDecl.name)) {
            // @Error better error reporting with pos and code
            throw new RuntimeException("Function %s already declared."
                    .formatted(funcDecl.name));
        }

        if (scope.knownFunc.containsKey(funcDecl.name) && allTypeResolved(funcDecl)) {
            return funcDecl.type;
        }

        // @Improvement visit body only once for all args?
        // funcDecl.args.forEach(arg -> tryInferArg(arg, funcDecl.body));

        // infer return type
        AstType inferedReturnType = funcDecl.body.code.size() == 1
                ? inferSingleExpressionFunctionReturnType(funcDecl)
                : inferFunctionReturnType(funcDecl);

        scope.knownFunc.put(funcDecl.name, funcDecl);
        return null;
    }

    private void tryInferArg(AstFunctionArgs arg, AstCodeBlock body) {
        // todo
    }

    private boolean allTypeResolved(AstFunctionDeclaration funcDecl) {
        if (funcDecl.type.isKnown())  return true;
        boolean allArgsKnown = funcDecl.args.stream()
                .map(AstFunctionArgs::type)
                .allMatch(AstType::isKnown);
        if (!allArgsKnown) return false;
        // todo
        return true;

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
//        pushScope(new Scope());
        codeBlock.code.forEach(cb -> cb.visit(this));
//        popScope();
        return AstType.NO_TYPE;
    }

    @Override
    public AstType visit(AstVariableDeclaration varDecl) {

        // @Error better error reporting with pos and code
//        if (scope.isAlreadyKnown(varDecl.varName)) {
//            throw new RuntimeException("Variable %s already declared.".formatted(varDecl.varName));
//        }

        if (varDecl.initExpr instanceof AstLambdaExpression) {
            inferLambdaVarDecl(varDecl);
            return varDecl.type;
        }


        AstType typeDeclared = varDecl.type;
        AstExpression initExpr = varDecl.initExpr;

        assert (initExpr != null || typeDeclared.isKnown()) : "[SHOULD NEVER HAPPEND] Variable %s has no init expr and type is unknown".formatted(varDecl.varName);

        // case x : Int = 5
        if (initExpr != null && typeDeclared.isKnown()) {
//            this.source = varDecl;
            AstType initExprInferedType = initExpr.visit(this);
            if (!typeDeclared.equals(initExprInferedType)) {
                // @Error better error reporting with pos and code
                throw new MismatchTypeException(
                        "%s\nVariable declaration %s was declared with [%s] but was infered to [%s] by expression %s"
                        .formatted(varDecl.toString(), varDecl.varName, typeDeclared, initExprInferedType, initExpr.toString()),
                        varDecl);
            }
            varDecl.type = initExprInferedType;
//            scope.knownVariables.put(varDecl.varName, varDecl);
            return initExprInferedType;
        }

        // case x : Int
        if (initExpr == null) {
            return typeDeclared;
        }

        // case c := 1 + 2
        AstType initExprInferedType = initExpr.visit(this);
        varDecl.type = initExprInferedType;
//        scope.knownVariables.put(varDecl.varName, varDecl);
        return initExprInferedType;
    }

    private void inferLambdaVarDecl(AstVariableDeclaration varDecl) {
        AstLambdaExpression lambda = (AstLambdaExpression) varDecl.initExpr;
        if (!varDecl.type.isKnown())  {
            ensureAllArgsTypeAreKnown(varDecl.varName, lambda);
        }
        List<AstType> argTypes = lambda.args.stream().map(AstLambdaArgs::type).toList();
        varDecl.type = new AstTypeFunction(argTypes, AstType.UNKNOWN, varDecl.start, varDecl.stop);
    }

    private void ensureAllArgsTypeAreKnown(String decl, AstLambdaExpression lambda) {
        lambda.args.forEach(arg -> {
            if (arg.type() == null || !arg.type().isKnown) {
                // @Error better error reporting
                String msg = "Cannot infer type of Lambda expression '%s': type of arg %s is unknown".formatted(decl, arg.name());
                throw new UnknownTypeException(msg, lambda);
            }
        });
    }

    @Override
    public AstType visit(AstLambdaExpression lambdaExpr) {
        if (lambdaExpr.type.isKnown()) return lambdaExpr.type;

//        if (this.source != null) {
//            if (this.source.type.isFuncType) {
//                AstTypeFunction lambdaType = (AstTypeFunction) this.source.type;
//                if (lambdaExpr.args.size() != lambdaType.args.size()) {
//                    // @Error
//                    String msg = "Lambda %s type was declared as %s with %n arg, but was given only %n arg : %s "
//                            .formatted(
//                                    this.source.varName,
//                                    lambdaType,
//                                    lambdaType.args.size(),
//                                    lambdaExpr.args.size(),
//                                    lambdaExpr.args.stream().map(arg -> arg.type().toString())
//                                            .collect(joining(" ")));
//                    throw new MismatchTypeException(msg, this.source);
//                }
//            }
//        }
//        // todo other checks
//        this.source = null;
        return lambdaExpr.type;
    }


    @Override
    public AstType visit(AstVariableAssignement assign) {
        // @Error better error reporting with pos and code
//        AstVariableDeclaration decl = scope.getVarByNameOrThrow(assign.varName,
//            new RuntimeException("Variable %s not declared, cannot assign value %s to it."
//                    .formatted(assign.varName, assign.expr.toString())));
//        AstType varType = decl.type;
//        AstType exprType = assign.expr.visit(this);
//        if (!varType.equals(exprType)) {
//            // @Error better error reporting with pos and code
//            throw new MismatchTypeException("Cannot assign value %s of type %s to variable %s of type %s"
//                .formatted(assign.expr.toString(), exprType, assign.varName, varType), assign);
//        }
//        decl.type = exprType;
        return super.visit(assign);
    }

    @Override
    public AstType visit(AstVariableReference ref) {
        // @Error better error reporting with pos and code
//        AstVariableDeclaration decl = scope.getVarByNameOrThrow(ref.varName,
//                new RuntimeException("Cannot find reference for variable %s".formatted(ref.varName)));
//        AstType typeKnown = scope.findTypeFor(ref.varName);
//        if (typeKnown.isKnown()) {
//            ref.type = typeKnown;
//            return ref.type;
//        }
//        return decl.type;
        return super.visit(ref);
    }
}
