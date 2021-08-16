package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.ast.AstVariableAssignement;

public class AssignementToUnknownVariableException extends RuntimeException {

    public AstVariableAssignement varAssign;

    public AssignementToUnknownVariableException(AstVariableAssignement varAssign, String message) {
        super(message);
        this.varAssign = varAssign;
    }
}
