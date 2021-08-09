package ca.applin.selmer.lang;

import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstDeclaration;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFuncCall;

public interface AstVisitor {
    void visit(AstBinop binop);
    void visit(AstCodeBlock codeBlock);
    void visit(AstDeclaration astDeclaration);
    void visit(AstExpression astExpression);
    void visit(AstFuncCall astExpression);

}
