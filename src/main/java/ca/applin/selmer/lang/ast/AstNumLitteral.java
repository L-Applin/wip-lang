package ca.applin.selmer.lang.ast;

public class AstNumLitteral extends AstExpression {
    public enum NumberType {
        FLOAT, INTEGER
    }

    public String value;
    public NumberType numberType;

    public AstNumLitteral(String value, NumberType numberType) {
        this.value = value;
        this.numberType = numberType;
    }

    @Override
    public String toString() {
        return "(%s:%s)".formatted(numberType, value);
    }
}
