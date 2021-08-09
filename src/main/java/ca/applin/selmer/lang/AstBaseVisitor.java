package ca.applin.selmer.lang;

import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstDeclaration;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFuncCall;

public class AstBaseVisitor implements AstVisitor {

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
}
