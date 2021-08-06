package ca.applin.selmer.lang;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class LangErrorListener extends BaseErrorListener {
    public static record ParseError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol,
                                    int line,
                                    int charPositionInLine,
                                    String msg,
                                    RecognitionException e) { }

    public List<ParseError> errors;

    public LangErrorListener() {
        this.errors = new ArrayList<>();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
            int charPositionInLine, String msg, RecognitionException e) {
        reportSyntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
    }

    public void reportSyntaxError(Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            RecognitionException e) {
        errors.add(new ParseError(recognizer, offendingSymbol, line, charPositionInLine, msg, e));
    }

}
