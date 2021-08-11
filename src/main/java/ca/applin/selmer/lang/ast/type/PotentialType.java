package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;

public class PotentialType extends AstType {

    public AstType infered;

    public PotentialType(AstType infered) {
        this.infered = infered;
        this.isKnown = false;
    }


    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
