package ca.applin.selmer.lang.ast;

public class AstReturnExpression extends AstExpression {
    public AstExpression expression;

    public AstReturnExpression(AstExpression expression) {
        this.expression = expression;
        this.isReturn = true;
    }


    @Override
    public String toString() {
        return "ReturnExpression:[%s]".formatted(expression.toString());
    }
}
