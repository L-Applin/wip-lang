package ca.applin.selmer.lang.ast;

public class AstStringLitteral extends AstExpression {
    public String value;

    public AstStringLitteral(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(STRING:%s)".formatted(value);
    }
}
