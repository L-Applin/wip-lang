package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.ast.Ast;

public class TypeException extends RuntimeException {
    public Ast errorNode;

    public TypeException(String message, Ast errorNode) {
        super(message);
        this.errorNode = errorNode;
    }
}
