package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import org.antlr.v4.runtime.Token;

public class AstIfStatement extends AstStatement {
    public AstExpression check;
    public AstCodeBlock thenBlock;
    public AstCodeBlock elseBlock;

    public AstIfStatement(AstExpression check, AstCodeBlock thenBlock, AstCodeBlock elseBlock, Token start, Token stop) {
        super(start, stop);
        this.check = check;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return "IfStatement:[check=%s then=%s else=%s]".formatted(
                check.toString(),
                thenBlock.toString(),
                elseBlock == null ? "_" : elseBlock.toString());
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
