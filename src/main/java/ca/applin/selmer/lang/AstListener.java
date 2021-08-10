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
import ca.applin.selmer.lang.ast.type.AstTypeStruct;
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

public interface AstListener {
    void visit(Ast ast);
    void visit(AstBinop binop);
    void visit(AstCodeBlock codeBlock);
    void visit(AstDeclaration astDeclaration);
    void visit(AstExpression astExpression);
    void visit(AstFuncCall astExpression);
    void visit(AstFunctionDeclaration astFunctionDeclaration);
    void visit(AstIfStatement astIfStatement);
    void visit(AstNumLitteral astNumLitteral);
    void visit(AstReturnExpression astReturnExpression);
    void visit(AstStatement astStatement);
    void visit(AstStringLitteral astStringLitteral);
    void visit(AstTypeStruct astTypeStruct);
    void visit(AstStructMemberDeclaration astStructMemberDeclaration);
    void visit(AstTypeDeclaration astTypeDeclaration);
    void visit(AstUnop astUnop);
    void visit(AstVariableDeclaration astVariableDeclaration);
    void visit(AstVariableReference astVariableReference);
    void visit(AstSumType astSumType);
    void visit(AstType astType);
    void visit(AstTypeArray astTypeArray);
    void visit(AstTypeFunction astTypeFunction);
    void visit(AstTypePoly astTypePoly);
    void visit(AstTypeSimple astTypeSimple);
    void visit(AstTypeTuple astTypeTuple);

}
