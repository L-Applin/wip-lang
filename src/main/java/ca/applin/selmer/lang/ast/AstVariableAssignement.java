package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public class AstVariableAssignement extends AstStatement {

    private String varName;
    private AstExpression expr;

    public AstVariableAssignement(String varName, AstExpression expr) {
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
