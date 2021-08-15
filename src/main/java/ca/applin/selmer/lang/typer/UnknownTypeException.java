package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.ast.Ast;

public class UnknownTypeException extends TypeException {

    public UnknownTypeException(String message, Ast errorNode) {
        super(formatMsg(message, errorNode), errorNode);
    }

    private static String formatMsg(String message, Ast errorNode) {
        String prepend = "[%s:%s]".formatted(
                errorNode.start == null ? "" : errorNode.start.getText(),
                errorNode.stop == null ? "" : errorNode.stop.getText());
        return "%s %s".formatted(prepend, message);
    }


}
