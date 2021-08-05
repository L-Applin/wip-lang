package ca.applin.selmer.lang;

import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.visitor.LangAstVisitor;
import java.beans.BeanProperty;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class Main {

    public static void main(String[] args) {
        String[] toParse = new String[]{
            "myVar : Int = 5\n",
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
            "TestType A :: Type { test: A ; person: Person ; val: Int  }\n"
        };

        for (String str : toParse) {
            ANTLRInputStream input = new ANTLRInputStream(str);
            LangLexer lexer = new LangLexer(input);
            LangParser parser = new LangParser(new CommonTokenStream(lexer));
            System.out.printf("Parsing \"%s\"\n", str.replace("\n", ""));
            System.out.println(parser.lang().ast);
            System.out.println();
        }
    }

}