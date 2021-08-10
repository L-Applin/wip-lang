package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import java.util.ArrayList;
import java.util.List;

public class AstArrayLitteral extends AstExpression {

    public List<AstExpression> elems;

    public AstArrayLitteral(List<AstExpression> elems) {
        this.elems = elems;
    }

    public AstArrayLitteral() {
        this(new ArrayList<>());
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
