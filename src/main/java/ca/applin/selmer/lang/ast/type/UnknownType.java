package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;

public class UnknownType extends AstType {

    public static AstType INSTANCE = new UnknownType();

    @Override
    public boolean isKnown() {
        return false;
    }

    @Override
    public String toString() {
        return "<UNKNOWN/>";
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
