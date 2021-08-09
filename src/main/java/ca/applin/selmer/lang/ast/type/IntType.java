package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;

public class IntType extends AstTypeSimple {

    public static final IntType INSTANCE = new IntType();

    public IntType() {
        super("Int");
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
