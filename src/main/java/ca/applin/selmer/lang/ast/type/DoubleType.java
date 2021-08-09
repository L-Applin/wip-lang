package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;

public class DoubleType extends AstTypeSimple {

    public static final DoubleType INSTANCE = new DoubleType();

    private DoubleType() {
        super("Double");
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
