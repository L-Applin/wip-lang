package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

public class UnknownType extends AstType {

    public static AstType DEFAULT_INSTANCE = new UnknownType();

    public UnknownType(Token start, Token stop) {
        super(start, stop);
    }

    public UnknownType() {
        super(new CommonToken(-1, "<Unknown>"), new CommonToken(-1, "<Unknown>"));
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
