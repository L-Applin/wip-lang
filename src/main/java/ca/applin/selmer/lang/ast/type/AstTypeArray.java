package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.Token;

public class AstTypeArray extends AstType {
    public AstType baseType;

    public AstTypeArray(AstType baseType, Token start, Token stop) {
        super(start, stop);
        this.baseType = baseType;
    }

    @Override
    public String toString() {
        return "Array:[" + baseType.toString() + "]";
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
