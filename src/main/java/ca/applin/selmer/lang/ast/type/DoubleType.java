package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.CommonToken;

public class DoubleType extends AstTypeSimple {

    public static final DoubleType INSTANCE = new DoubleType();

    private DoubleType() {
        super("Double", new CommonToken(-1, "<double type>"), new CommonToken(-1, "<double type>"));
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
