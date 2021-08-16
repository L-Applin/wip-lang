package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.Token;

public class AstArrayAccessor extends AstExpression {

    public AstExpression arrayExpre;
    public AstExpression argument;

    public AstArrayAccessor(AstExpression arrayExpre, AstExpression argument, Token start, Token stop) {
        super(start, stop);
        this.arrayExpre = arrayExpre;
        this.argument = argument;
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
