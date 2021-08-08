package ca.applin.selmer.lang.ast.type;

class UnknownType extends AstType {

    public static AstType INSTANCE = new UnknownType();

    @Override
    public boolean isKnown() {
        return false;
    }

    @Override
    public String toString() {
        return "<UNKNOWN/>";
    }
}
