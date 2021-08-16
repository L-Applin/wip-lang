package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

public class AstArrayLitteral extends AstExpression {

    public List<AstExpression> elems;

    public AstArrayLitteral(List<AstExpression> elems, Token start, Token stop) {
        super(start, stop);
        this.elems = elems;
    }

    public AstArrayLitteral(Token start, Token stop) {
        this(new ArrayList<>(), start, stop);
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
