package ca.applin.selmer.lang.ast;

import org.antlr.v4.runtime.Token;

public abstract class AstStatement extends Ast {

    public AstStatement(Token start, Token stop) {
        super(start, stop);
    }
}
