package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.DoubleType;
import ca.applin.selmer.lang.ast.type.IntType;

public class AstNumLitteral extends AstExpression {
    public enum NumberType {
        FLOAT(DoubleType.INSTANCE),
        INTEGER(IntType.INSTANCE);
        public final AstType type;
        NumberType(AstType astType) {
            this.type = astType;
        }

    }

    public String value;
    public NumberType numberType;

    public AstNumLitteral(String value, NumberType numberType) {
        this.value = value;
        this.numberType = numberType;
        this.type = numberType.type;
    }

    @Override
    public String toString() {
        return "(%s:%s)".formatted(numberType, value);
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
