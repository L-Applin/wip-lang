package ca.applin.selmer.lang.ast;

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
        return "(binop op=%s left=%s right=%s)".formatted(
                operator + ":" +operator.str, left.toString(), right.toString()
        );
    }
}
