package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.StringType;
import org.antlr.v4.runtime.Token;

public class AstStringLitteral extends AstExpression {
    public String value;

    public AstStringLitteral(String value, Token start, Token stop) {
        super(start, stop);
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
