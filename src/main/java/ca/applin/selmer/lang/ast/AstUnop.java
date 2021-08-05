package ca.applin.selmer.lang.ast;

public class AstUnop extends AstExpression {

    public enum UnopType {
        PRE, POST;
    }

    public AstExpression expr;
    public Operator operator;
    public UnopType unopType;

    public AstUnop(AstExpression expr, Operator operator,
            UnopType unopType) {
        this.expr = expr;
        this.operator = operator;
        this.unopType = unopType;
    }

    @Override
    public String toString() {
        return "(Unop %s operator=%s, expr=%s)".formatted(
                unopType, operator, expr.toString()
        );
    }
}
