package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;

public class UnknownType extends AstType {

    public static AstType INSTANCE = new UnknownType();

    public UnknownType() {
        this.isKnown = false;
    }


    @Override
    public String toString() {
        return "<UNKNOWN/>";
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
