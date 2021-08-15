package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.ast.Ast;
import java.net.Proxy.Type;

public class MismatchTypeException extends TypeException {

    public MismatchTypeException(String message, Ast errorNode) {
        super(message, errorNode);
    }
}
