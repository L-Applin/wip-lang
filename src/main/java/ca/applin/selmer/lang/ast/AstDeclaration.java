package ca.applin.selmer.lang.ast;

import java.util.Optional;
import org.antlr.v4.runtime.Token;

public abstract class AstDeclaration extends Ast {

    public AstDeclaration(Token start, Token stop) {
        super(start, stop);
    }
}
