package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.StringType;

public class AstStringLitteral extends AstExpression {
    public String value;

    public AstStringLitteral(String value) {
        this.value = value;
        this.type = StringType.INSTANCE;
    }

    @Override
    public String toString() {
        return "(STRING:%s)".formatted(value);
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
