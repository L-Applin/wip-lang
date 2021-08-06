package ca.applin.selmer.lang;

import ca.applin.selmer.lang.LangErrorListener.ParseError;
import ca.applin.selmer.lang.LangParser.LangContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class Main {

    public static void main(String[] args) {
        String[] toParse = new String[]{
            "myVar  Int == 5\n",
            "myVar : Int\n",
            "myVar := 5;",
            "1 + 2",
            "(1 + 2)",
            "1 + 2 * 3 + 4",
            "(1 + 2) * (3 + 4)",
            "\"this is a 'string' litteral\"",
            "10 + plus(1 + 2, 3)",
            "1 - ++i",
            "Either A B :: Type = Left A | Right B ;",
            "Parser A B :: Type = String -> (Maybe A, B)\n",
            "EmptyType :: Type { };",
            "Person :: Type { name: String }\n",
            "Person :: Type { name: String ; age: Int = -1 }\n",
            "TestType A :: Type { test: A ; person: Person ; val: Int  }\n",
            "four : Bool = 2 == 2\n"
        };

        for (String str : toParse) {
            ANTLRInputStream input = new ANTLRInputStream(str);
            LangLexer lexer = new LangLexer(input);
            LangParser parser = new LangParser(new CommonTokenStream(lexer));
            System.out.printf("Parsing \"%s\"\n", str.replace("\n", ""));
            parser.removeErrorListeners();
            LangErrorListener listener = new LangErrorListener();
            parser.addErrorListener(listener);
            LangContext langContext = parser.lang();
            RuntimeException ex = null;
            if (listener.errors.size() > 0) {
                for (ParseError p : listener.errors) {
                    ex = p.e();
                    System.err.println(p);
                }
            }
            if (ex != null) throw ex;
            System.out.println(langContext.ast);
            System.out.println();
        }
    }

}