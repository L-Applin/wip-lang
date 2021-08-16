package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.Token;

public class PotentialType extends AstType {

    public AstType infered;

    public PotentialType(AstType infered, Token start, Token stop) {
        super(start, stop);
        this.infered = infered;
        this.isKnown = false;
    }


    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
