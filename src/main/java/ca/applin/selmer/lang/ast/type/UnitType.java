package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

public class UnitType extends AstType {

    public static AstType DEFAULT_INSTANCE = new UnitType();

    public UnitType(Token start, Token stop) {
        super(start, stop);
    }

    public UnitType() {
        this(new CommonToken(-1, "<Unknown>"), new CommonToken(-1, "<Unknown>"));
        this.isKnown = false;
    }


    @Override
    public String toString() {
        return "Unit";
    }
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
