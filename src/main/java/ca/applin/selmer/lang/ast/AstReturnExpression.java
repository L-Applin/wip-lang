package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.Token;

public class AstReturnExpression extends AstExpression {
    public AstExpression expression;

    public AstReturnExpression(AstExpression expression, Token start, Token stop) {
        super(start, stop);
        this.expression = expression;
        this.isReturn = true;
    }

    @Override
    public String toString() {
        return "ReturnExpression:[%s]".formatted(expression.toString());
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
