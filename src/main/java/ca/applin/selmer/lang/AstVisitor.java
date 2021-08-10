package ca.applin.selmer.lang;

import ca.applin.selmer.lang.ast.*;
import ca.applin.selmer.lang.ast.type.*;

public interface AstVisitor<T> {
    T visit(Ast ast);
    T visit(AstArrayAccessor astArrayAccessor);
    T visit(AstArrayLitteral astArrayLitteral);
    T visit(AstBinop binop);
    T visit(AstCodeBlock codeBlock);
    T visit(AstDeclaration astDeclaration);
    T visit(AstExpression astExpression);
    T visit(AstForStatement astForStatement);
    T visit(AstFuncCall astExpression);
    T visit(AstFunctionDeclaration astFunctionDeclaration);
    T visit(AstIfStatement astIfStatement);
    T visit(AstLambdaExpression astLambdaExpression);
    T visit(AstNumLitteral astNumLitteral);
    T visit(AstReturnExpression astReturnExpression);
    T visit(AstStatement astStatement);
    T visit(AstStringLitteral astStringLitteral);
    T visit(AstTypeStruct astTypeStruct);
    T visit(AstStructMemberDeclaration astStructMemberDeclaration);
    T visit(AstTypeDeclaration astTypeDeclaration);
    T visit(AstStructMemberAssignement astStructMemberAssignement);
    T visit(AstUnop astUnop);
    T visit(AstVariableAssignement astVariableAssignement);
    T visit(AstVariableDeclaration astVariableDeclaration);
    T visit(AstVariableReference astVariableReference);
    T visit(AstSumType astSumType);
    T visit(AstType astType);
    T visit(AstTypeArray astTypeArray);
    T visit(AstTypeFunction astTypeFunction);
    T visit(AstTypePoly astTypePoly);
    T visit(AstTypeSimple astTypeSimple);
    T visit(AstTypeTuple astTypeTuple);
    T visit(AstWhileStatement astWhileStatement);
}
