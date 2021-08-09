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
import ca.applin.selmer.lang.ast.AstStructDeclaration;
import ca.applin.selmer.lang.ast.AstStructMemberDeclaration;
import ca.applin.selmer.lang.ast.AstTypeDeclaration;
import ca.applin.selmer.lang.ast.AstUnop;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.type.AstSumType;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.AstTypeArray;
import ca.applin.selmer.lang.ast.type.AstTypeFunction;
import ca.applin.selmer.lang.ast.type.AstTypePoly;
import ca.applin.selmer.lang.ast.type.AstTypeSimple;
import ca.applin.selmer.lang.ast.type.AstTypeTuple;

public class AstBaseListener implements AstListener {

    @Override
    public void visit(Ast ast) {

    }

    @Override
    public void visit(AstBinop binop) {

    }

    @Override
    public void visit(AstCodeBlock codeBlock) {

    }

    @Override
    public void visit(AstDeclaration astDeclaration) {

    }

    @Override
    public void visit(AstExpression astExpression) {

    }

    @Override
    public void visit(AstFuncCall astExpression) {

    }

    @Override
    public void visit(AstFunctionDeclaration astFunctionDeclaration) {

    }

    @Override
    public void visit(AstIfStatement astIfStatement) {

    }

    @Override
    public void visit(AstNumLitteral astNumLitteral) {

    }

    @Override
    public void visit(AstReturnExpression astReturnExpression) {

    }

    @Override
    public void visit(AstStatement astStatement) {

    }

    @Override
    public void visit(AstStringLitteral astStringLitteral) {

    }

    @Override
    public void visit(AstStructDeclaration astStructDeclaration) {

    }

    @Override
    public void visit(AstStructMemberDeclaration astStructMemberDeclaration) {

    }

    @Override
    public void visit(AstTypeDeclaration astTypeDeclaration) {

    }

    @Override
    public void visit(AstUnop astUnop) {

    }

    @Override
    public void visit(AstVariableDeclaration astVariableDeclaration) {

    }

    @Override
    public void visit(AstVariableReference astVariableReference) {

    }

    @Override
    public void visit(AstSumType astSumType) {

    }

    @Override
    public void visit(AstType astType) {

    }

    @Override
    public void visit(AstTypeArray astTypeArray) {

    }

    @Override
    public void visit(AstTypeFunction astTypeFunction) {

    }

    @Override
    public void visit(AstTypePoly astTypePoly) {

    }

    @Override
    public void visit(AstTypeSimple astTypeSimple) {

    }

    @Override
    public void visit(AstTypeTuple astTypeTuple) {

    }
}
