package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.scope.Scope;
import org.antlr.v4.runtime.Token;

public abstract class Ast {

    public Token start, stop;
    public Ast parent;
    public Scope scope;

    public void setTokens(Token start, Token stop) {
       this.start = start;
       this.stop = stop;
    }

    public abstract <T> T visit(AstVisitor<T> visitor);
}
