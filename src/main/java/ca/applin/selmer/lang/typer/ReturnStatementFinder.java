package ca.applin.selmer.lang.typer;

import static java.util.function.Function.identity;

import ca.applin.selmer.lang.AstBaseVisitor;
import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.ast.AstArrayAccessor;
import ca.applin.selmer.lang.ast.AstArrayLitteral;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstDeclaration;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstForStatement;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstIfStatement;
import ca.applin.selmer.lang.ast.AstLambdaExpression;
import ca.applin.selmer.lang.ast.AstNumLitteral;
import ca.applin.selmer.lang.ast.AstReturnExpression;
import ca.applin.selmer.lang.ast.AstStatement;
import ca.applin.selmer.lang.ast.AstStringLitteral;
import ca.applin.selmer.lang.ast.AstStructMemberAssignement;
import ca.applin.selmer.lang.ast.AstStructMemberDeclaration;
import ca.applin.selmer.lang.ast.AstUnop;
import ca.applin.selmer.lang.ast.AstVariableAssignement;
import ca.applin.selmer.lang.ast.type.AstSumType;
import ca.applin.selmer.lang.ast.type.AstTypeArray;
import ca.applin.selmer.lang.ast.type.AstTypeFunction;
import ca.applin.selmer.lang.ast.type.AstTypePoly;
import ca.applin.selmer.lang.ast.type.AstTypeSimple;
import ca.applin.selmer.lang.ast.type.AstTypeStruct;
import ca.applin.selmer.lang.ast.AstTypeDeclaration;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.AstWhileStatement;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.AstTypeTuple;
import ca.applin.selmer.lang.scope.Scope;
import java.util.ArrayList;
import java.util.List;

public class ReturnStatementFinder extends AstBaseVisitor<List<AstReturnExpression>> {

    @Override
    public List<AstReturnExpression> visit(AstReturnExpression astReturnExpression) {
        return new ArrayList<>(){{ add(astReturnExpression); }};
    }

    @Override
    public List<AstReturnExpression> visit(AstCodeBlock codeBlock) {
        return codeBlock.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList();
    }

    @Override
    public List<AstReturnExpression> visit(AstIfStatement astIfStatement) {
        List<AstReturnExpression> thenReturns = astIfStatement.thenBlock.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList();
        List<AstReturnExpression> elseReturns = astIfStatement.elseBlock.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList();
        List<AstReturnExpression> types = new ArrayList<>();
        types.addAll(thenReturns);
        types.addAll(elseReturns);
        return types;
    }

    @Override
    public List<AstReturnExpression> visit(AstVariableDeclaration astVariableDeclaration) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstWhileStatement astWhileStatement) {
        return new ArrayList<>(astWhileStatement.code.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList());
    }

    @Override
    public List<AstReturnExpression> visit(AstVariableReference astVariableReference) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstFunctionDeclaration astFunctionDeclaration) {
        return astFunctionDeclaration.body.visit(this);
    }

    @Override
    public List<AstReturnExpression> visit(AstTypeStruct astTypeStruct) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstTypeDeclaration astTypeDeclaration) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(Ast ast) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstArrayAccessor astArrayAccessor) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstArrayLitteral astArrayLitteral) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstBinop binop) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstDeclaration astDeclaration) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstExpression astExpression) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstForStatement astForStatement) {
        List<AstReturnExpression> forBlock = astForStatement.code.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList();
        List<AstReturnExpression> types = new ArrayList<>();
        types.addAll(forBlock);
        return types;

    }

    @Override
    public List<AstReturnExpression> visit(AstFuncCall astExpression) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstLambdaExpression astLambdaExpression) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstNumLitteral astNumLitteral) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstStatement astStatement) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstStringLitteral astStringLitteral) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstStructMemberDeclaration astStructMemberDeclaration) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstStructMemberAssignement astStructMemberAssignement) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstUnop astUnop) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstVariableAssignement astVariableAssignement) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstSumType astSumType) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstType astType) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstTypeArray astTypeArray) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstTypeFunction astTypeFunction) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstTypePoly astTypePoly) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstTypeSimple astTypeSimple) {
        return new ArrayList<>();
    }

    @Override
    public List<AstReturnExpression> visit(AstTypeTuple astTypeTuple) {
        return new ArrayList<>();
    }
}
