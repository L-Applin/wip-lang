package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public class AstForStatement extends AstStatement {

    public AstExpression iterator;
    public AstCodeBlock code;

    public AstForStatement(AstExpression iterator, AstCodeBlock code) {
        this.iterator = iterator;
        this.code = code;
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
