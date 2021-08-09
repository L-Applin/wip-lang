package ca.applin.selmer.lang.ast;

public class AstIfStatement extends AstStatement {
    public AstExpression check;
    public AstCodeBlock thenBlock;
    public AstCodeBlock elseBlock;

    public AstIfStatement(AstExpression check, AstCodeBlock thenBlock, AstCodeBlock elseBlock) {
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
}
