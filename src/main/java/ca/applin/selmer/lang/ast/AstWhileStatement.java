package ca.applin.selmer.lang.ast;

public class AstWhileStatement extends AstStatement {
    public AstExpression check;
    public AstCodeBlock code;

    public AstWhileStatement(AstExpression check, AstCodeBlock code) {
        this.check = check;
        this.code = code;
    }
}
