package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.CommonToken;

public class IntType extends AstTypeSimple {

    public static final IntType INSTANCE = new IntType();

    public IntType() {
        super("Int", new CommonToken(-1, "<int type>"), new CommonToken(-1, "<int type>"));
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
