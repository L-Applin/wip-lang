package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import org.antlr.v4.runtime.Token;

public abstract class AstExpression extends Ast {
    public AstType type;
    public boolean isReturn;

    public AstExpression(Token start, Token stop) {
        super(start, stop);
        this.type = AstType.UNKNOWN;
    }

    public AstExpression(AstType type, Token start, Token stop) {
        super(start, stop);
        this.type = type;
    }
}
