package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import java.util.List;

public class AstStructMemberAssignement extends AstStatement {

    public List<String> derefs;
    public AstExpression value;

    public AstStructMemberAssignement(List<String> derefs, AstExpression value) {
        this.derefs = derefs;
        this.value = value;
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
