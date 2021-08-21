package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.Token;

public class AstWhileStatement extends AstStatement {
    public AstExpression check;
    public AstCodeBlock code;

    public AstWhileStatement(AstExpression check, AstCodeBlock code, Token start, Token stop) {
        super(start, stop);
        this.check = check;
        this.code = code;
    }

    @Override
    public String toString() {
        return "WhileStmt[check=%s code=%s]".formatted(check.toString(), code.toString());
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
