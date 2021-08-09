package ca.applin.selmer.lang;

import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstDeclaration;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstIfStatement;
import ca.applin.selmer.lang.ast.AstNumLitteral;
import ca.applin.selmer.lang.ast.AstReturnExpression;
import ca.applin.selmer.lang.ast.AstStatement;
import ca.applin.selmer.lang.ast.AstStringLitteral;
import ca.applin.selmer.lang.ast.AstStructMemberDeclaration;
import ca.applin.selmer.lang.ast.AstTypeDeclaration;
import ca.applin.selmer.lang.ast.AstUnop;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.AstStructDeclaration;
import ca.applin.selmer.lang.ast.AstWhileStatement;
import ca.applin.selmer.lang.ast.type.AstSumType;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.AstTypeArray;
import ca.applin.selmer.lang.ast.type.AstTypeFunction;
import ca.applin.selmer.lang.ast.type.AstTypePoly;
import ca.applin.selmer.lang.ast.type.AstTypeSimple;
import ca.applin.selmer.lang.ast.type.AstTypeTuple;

public class AstBaseVisitor<T> implements AstVisitor<T> {

    @Override
    public T visit(Ast ast) {
        return null;
    }

    @Override
    public T visit(AstBinop binop) {
        return null;
    }

    @Override
    public T visit(AstCodeBlock codeBlock) {
        return null;
    }

    @Override
    public T visit(AstDeclaration astDeclaration) {
        return null;
    }

    @Override
    public T visit(AstExpression astExpression) {
        return null;
    }

    @Override
    public T visit(AstFuncCall astExpression) {
        return null;
    }

    @Override
    public T visit(AstFunctionDeclaration astFunctionDeclaration) {
        return null;
    }

    @Override
    public T visit(AstIfStatement astIfStatement) {
        return null;
    }

    @Override
    public T visit(AstNumLitteral astNumLitteral) {
        return null;
    }

    @Override
    public T visit(AstReturnExpression astReturnExpression) {
        return null;
    }

    @Override
    public T visit(AstStatement astStatement) {
        return null;
    }

    @Override
    public T visit(AstStringLitteral astStringLitteral) {
        return null;
    }

    @Override
    public T visit(AstStructDeclaration astStructDeclaration) {
        return null;
    }

    @Override
    public T visit(AstStructMemberDeclaration astStructMemberDeclaration) {
        return null;
    }

    @Override
    public T visit(AstTypeDeclaration astTypeDeclaration) {
        return null;
    }

    @Override
    public T visit(AstUnop astUnop) {
        return null;
    }

    @Override
    public T visit(AstVariableDeclaration astVariableDeclaration) {
        return null;
    }

    @Override
    public T visit(AstVariableReference astVariableReference) {
        return null;
    }

    @Override
    public T visit(AstSumType astSumType) {
        return null;
    }

    @Override
    public T visit(AstType astType) {
        return null;
    }

    @Override
    public T visit(AstTypeArray astTypeArray) {
        return null;
    }

    @Override
    public T visit(AstTypeFunction astTypeFunction) {
        return null;
    }

    @Override
    public T visit(AstTypePoly astTypePoly) {
        return null;
    }

    @Override
    public T visit(AstTypeSimple astTypeSimple) {
        return null;
    }

    @Override
    public T visit(AstTypeTuple astTypeTuple) {
        return null;
    }

    @Override
    public T visit(AstWhileStatement astWhileStatement) {
        return null;
    }
}
