package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public class AstBinop extends AstExpression {
    public AstExpression left;
    public AstExpression right;
    public Operator operator;

    public AstBinop(AstExpression left, AstExpression right,
            Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "(binop op=%s %sleft=%s right=%s)".formatted(
                operator + ":" +operator.str,
                type.isKnown() ? "type:" + type.toString() +" ": "",
                left.toString(),
                right.toString()
        );
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
