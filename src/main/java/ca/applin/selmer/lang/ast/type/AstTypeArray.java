package ca.applin.selmer.lang.ast.type;

public class AstTypeArray implements AstType {
    public AstType baseType;

    public AstTypeArray(AstType baseType) {
        this.baseType = baseType;
    }

    @Override
    public String toString() {
        return "Array:[" + baseType.toString() + "]";
    }
}
