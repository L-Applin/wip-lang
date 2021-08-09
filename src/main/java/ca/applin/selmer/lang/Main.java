package ca.applin.selmer.lang;

import ca.applin.selmer.lang.LangErrorListener.ParseError;
import ca.applin.selmer.lang.LangParser.LangContext;
import java.util.function.Function;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

    public static void main(String[] args) {
        String[] toParse = new String[]{
            "myVar:Int\n\n\n\n\n\n\n\n",
            "myVar:Int;",
//            "1 + 2",
            "x := 5;",
            "x : Int = (5 + 2) * 3;",
//            "(1 + 2)",
//            "1 + 2 * 3 + 4",
//            "(1 + 2) * (3 + 4)",
//            "\"this is a 'string' litteral\"",
//            "10 + plus(1 + 2, 3)",
//            "1 - ++i",
            "Test :: Type = (Int, String) ;",
            "Either A B :: Type = Left A | Right B ;",
            "Parser A B :: Type = String -> (Maybe A, B)\n",
            "EmptyType :: Type { }\n",
            "Person :: Type { name: String }\n",
            "Person :: Type { name: String ; age: Int = -1 }\n",
            "TestType A :: Type { test: A ; person: Person ; val: Int  }\n",
            "four : Bool = 2 == 2\n",
            "Predicate :: Type = A -> B -> Bool\n",
            "HttpMethod :: Type = GET | POST\n",
            "{ \n }",
            """
                  {
                    myVar : Int
                    myVar2 : Int = 5 + 6
                    myVar3 := f(1 + 2, 3)
                  }
            """,
            "myFun :: Int -> Bool = (x:Int) -> { val:Bool = x % 2 == 0\n }"
        };
        boolean printTokens = false;
        for (String str : toParse) {
            ANTLRInputStream input = new ANTLRInputStream(str);
            LangLexer lexer = new LangLexer(input);
            CommonTokenStream inputStream = new CommonTokenStream(lexer);
            if (printTokens) {
                printTokens(inputStream);
            }
            parseAndPrint(str, inputStream);
        }
    }

    private static void printTokens(CommonTokenStream inputStream) {
        inputStream.fill();
        System.out.println(inputStream.getTokens());
    }

    private static  void parseAndPrint(String str, CommonTokenStream inputStream) {
        LangParser parser = new LangParser(inputStream);
        parser.setProfile(true);
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