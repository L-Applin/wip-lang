package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.ast.AstVariableReference;

public class UnkownVariableReferenceException extends RuntimeException {

    public AstVariableReference varRef;

    public UnkownVariableReferenceException(AstVariableReference varRef, String message) {
        super(message);
        this.varRef = varRef;
    }
}
