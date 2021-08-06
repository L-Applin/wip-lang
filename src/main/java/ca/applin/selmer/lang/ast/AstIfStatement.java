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
}
