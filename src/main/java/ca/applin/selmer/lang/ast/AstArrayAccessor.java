package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public class AstArrayAccessor extends AstExpression {

    public AstExpression arrayExpre;
    public AstExpression argument;

    public AstArrayAccessor(AstExpression arrayExpre, AstExpression argument) {
        this.arrayExpre = arrayExpre;
        this.argument = argument;
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
