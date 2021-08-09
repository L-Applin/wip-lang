package ca.applin.selmer.lang.typer;

public class MismatchTypeException extends RuntimeException{

    public MismatchTypeException() {
    }

    public MismatchTypeException(String message) {
        super(message);
    }

    public MismatchTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MismatchTypeException(Throwable cause) {
        super(cause);
    }

    public MismatchTypeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
