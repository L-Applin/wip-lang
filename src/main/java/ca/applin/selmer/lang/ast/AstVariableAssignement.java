package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public class AstVariableAssignement extends AstStatement {

    public String varName;
    public AstExpression expr;

    public AstVariableAssignement(String varName, AstExpression expr) {
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "VarAssign:[name=%s expr=%s]".formatted(varName, expr.toString());
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
