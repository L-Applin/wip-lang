package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.AstBaseVisitor;
import ca.applin.selmer.lang.LangLexer;
import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.ast.AstArrayAccessor;
import ca.applin.selmer.lang.ast.AstArrayLitteral;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstForStatement;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstIfStatement;
import ca.applin.selmer.lang.ast.AstLambdaExpression;
import ca.applin.selmer.lang.ast.AstNumLitteral;
import ca.applin.selmer.lang.ast.AstReturnExpression;
import ca.applin.selmer.lang.ast.AstStringLitteral;
import ca.applin.selmer.lang.ast.AstStructMemberAssignement;
import ca.applin.selmer.lang.ast.AstStructMemberDeclaration;
import ca.applin.selmer.lang.ast.AstTypeDeclaration;
import ca.applin.selmer.lang.ast.AstUnop;
import ca.applin.selmer.lang.ast.AstVariableAssignement;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.AstWhileStatement;
import ca.applin.selmer.lang.ast.type.AstSumType;
import ca.applin.selmer.lang.ast.type.AstTypeArray;
import ca.applin.selmer.lang.ast.type.AstTypeFunction;
import ca.applin.selmer.lang.ast.type.AstTypePoly;
import ca.applin.selmer.lang.ast.type.AstTypeSimple;
import ca.applin.selmer.lang.ast.type.AstTypeStruct;
import ca.applin.selmer.lang.ast.type.AstTypeTuple;
import ca.applin.selmer.lang.scope.Scope;
import java.util.List;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Interval;

/**
 * This {@link ca.applin.selmer.lang.AstVisitor visitor} sets new instances of {@link Scope scopes}
 * to all {@link Ast AST} nodes and sets the parent of those nodes accordingly.
 */
public class Scoper extends AstBaseVisitor<Ast> {

    private void visitChildrenNodes(Ast parent, Ast child) {
        child.parent = parent;
        child.scope = parent.scope;
        child.visit(this);
    }

    private void visitChildrenNodes(Ast parent, Ast ... childrens) {
        for (Ast child : childrens) { visitChildrenNodes(parent, child); }
    }

    private void visitChildrenNodes(Ast parent, List<? extends Ast> childrens) {
        for (Ast child : childrens) { visitChildrenNodes(parent, child); }
    }


    @Override
    public Ast visit(AstArrayAccessor arrayAst) {
        visitChildrenNodes(arrayAst, arrayAst.argument, arrayAst.arrayExpre);
        return arrayAst;
    }

    @Override
    public Ast visit(AstArrayLitteral astArrayLitteral) {
        visitChildrenNodes(astArrayLitteral, astArrayLitteral.elems);
        return astArrayLitteral;
    }

    @Override
    public Ast visit(AstBinop binop) {
        visitChildrenNodes(binop, binop.left, binop.right);
        return binop;
    }

    @Override
    public Ast visit(AstCodeBlock codeBlock) {
        Ast parent = codeBlock.parent;
        Scope parentScope = parent == null ? null : parent.scope;
        codeBlock.scope = new Scope(parentScope);
        visitChildrenNodes(codeBlock, codeBlock.code);
        return codeBlock;
    }

    @Override
    public Ast visit(AstForStatement astForStatement) {
        visitChildrenNodes(astForStatement, astForStatement.iterator, astForStatement.code);
        return astForStatement;
    }

    @Override
    public Ast visit(AstFuncCall funcCall) {
        visitChildrenNodes(funcCall, funcCall.base);
        visitChildrenNodes(funcCall, funcCall.args);
        return funcCall;
    }

    @Override
    public Ast visit(AstFunctionDeclaration funcDecl) {
        visitChildrenNodes(funcDecl, funcDecl.body);
        visitChildrenNodes(funcDecl, funcDecl.body);
        funcDecl.scope.knownFunc.put(funcDecl.name, funcDecl);
        return funcDecl;
    }

    @Override
    public Ast visit(AstIfStatement ifStmt) {
        visitChildrenNodes(ifStmt, ifStmt.check);
        visitChildrenNodes(ifStmt, ifStmt.thenBlock);
        if (ifStmt.elseBlock != null ) visitChildrenNodes(ifStmt, ifStmt.elseBlock);
        return ifStmt;
    }

    @Override
    public Ast visit(AstLambdaExpression lambda) {
        visitChildrenNodes(lambda, lambda.body);
        lambda.args.forEach(arg -> lambda.body.scope.knownLambdaArgs.put(arg.name(), arg));
        return lambda;
    }

    @Override
    public Ast visit(AstNumLitteral astNumLitteral) {
        return astNumLitteral;
    }

    @Override
    public Ast visit(AstReturnExpression retExpr) {
        visitChildrenNodes(retExpr, retExpr.expression);
        return retExpr;
    }

    @Override
    public Ast visit(AstStringLitteral astStringLitteral) {
        return astStringLitteral;
    }

    @Override
    public Ast visit(AstTypeStruct astTypeStruct) {
        visitChildrenNodes(astTypeStruct, astTypeStruct.members);
        return astTypeStruct;
    }

    @Override
    public Ast visit(AstStructMemberDeclaration members) {
        visitChildrenNodes(members, members.initExpr);
        return members;
    }

    @Override
    public Ast visit(AstStructMemberAssignement astStructMemberAssignement) {
        visitChildrenNodes(astStructMemberAssignement, astStructMemberAssignement.value);
        return astStructMemberAssignement;
    }

    @Override
    public Ast visit(AstTypeDeclaration astTypeDeclaration) {
        visitChildrenNodes(astTypeDeclaration, astTypeDeclaration.type);
        return astTypeDeclaration;
    }

    @Override
    public Ast visit(AstUnop astUnop) {
        visitChildrenNodes(astUnop, astUnop.expr);
        return astUnop;
    }

    @Override
    public Ast visit(AstVariableAssignement varAssing) {
        if (!varAssing.scope.containsVar(varAssing.varName)) {
            LangLexer source = (LangLexer) varAssing.start.getTokenSource();
            int startIndex = varAssing.expr.start.getStartIndex();
            int stopIndex = varAssing.expr.stop.getStopIndex();
            String str = source.getInputStream().getText(new Interval(startIndex, stopIndex));
            throw new AssignementToUnknownVariableException(varAssing,
                    "%s:[%s:%s] - cannot assign value '%s' to unknow variable '%s'.".formatted(
                            source.getSourceName(),
                            varAssing.start.getLine(),
                            varAssing.start.getCharPositionInLine(),
                            str,
                            varAssing.varName)
            );
        }
        visitChildrenNodes(varAssing, varAssing.expr);
        return varAssing;
    }

    @Override
    public Ast visit(AstVariableDeclaration varDecl) {
        if (varDecl.initExpr != null) {
            visitChildrenNodes(varDecl, varDecl.initExpr);
        }
        varDecl.scope.knownVariables.put(varDecl.varName, varDecl);
        return varDecl;
    }

    @Override
    public Ast visit(AstVariableReference varRef) {
        if (!varRef.scope.containsVar(varRef.varName)) {
            // @Error better error reporting
            throw new UnkownVariableReferenceException(varRef,
                    "%s:[%s:%s] - cannot find variable '%s' in current scope.".formatted(
                            varRef.start.getTokenSource().getSourceName(),
                            varRef.start.getLine(),
                            varRef.start.getCharPositionInLine(),
                            varRef.varName)
            );
        }
        return varRef;
    }

    @Override
    public Ast visit(AstSumType astSumType) {
        return astSumType;
    }

    @Override
    public Ast visit(AstTypeArray astTypeArray) {
        visitChildrenNodes(astTypeArray, astTypeArray.baseType);
        return astTypeArray;
    }

    @Override
    public Ast visit(AstTypeFunction funcType) {
        visitChildrenNodes(funcType, funcType.args);
        visitChildrenNodes(funcType, funcType.ret);
        return funcType;
    }

    @Override
    public Ast visit(AstTypePoly astTypePoly) {
        return astTypePoly;
    }

    @Override
    public Ast visit(AstTypeSimple astTypeSimple) {
        return astTypeSimple;
    }

    @Override
    public Ast visit(AstTypeTuple tupType) {
        visitChildrenNodes(tupType, tupType.types);
        return tupType;
    }

    @Override
    public Ast visit(AstWhileStatement whileStmt) {
        visitChildrenNodes(whileStmt, whileStmt.check);
        visitChildrenNodes(whileStmt, whileStmt.code);
        return whileStmt;
    }
}
