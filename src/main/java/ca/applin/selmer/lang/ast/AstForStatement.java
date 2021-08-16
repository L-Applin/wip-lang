package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.Token;

public class AstForStatement extends AstStatement {

    public AstExpression iterator;
    public AstCodeBlock code;

    public AstForStatement(AstExpression iterator, AstCodeBlock code, Token start, Token stop) {
        super(start, stop);
        this.iterator = iterator;
        this.code = code;
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
