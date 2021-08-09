package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public class AstWhileStatement extends AstStatement {
    public AstExpression check;
    public AstCodeBlock code;

    public AstWhileStatement(AstExpression check, AstCodeBlock code) {
        this.check = check;
        this.code = code;
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
