package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

public class VoidType extends AstType {

    public static AstType DEFAULT_INSTANCE = new VoidType();

    public VoidType(Token start, Token stop) {
        super(start, stop);
    }

    public VoidType() {
        this(new CommonToken(-1, "<Unknown>"), new CommonToken(-1, "<Unknown>"));
    }

    @Override
    public String toString() {
        return "VOID";
    }
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
