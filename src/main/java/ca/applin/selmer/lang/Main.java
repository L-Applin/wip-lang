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
            "Parser A :: Type = String -> (Maybe A, String)\n",
            "myVar : Int = 5\n",
            "myVar : Int\n",
            "myVar := 5\n",
            "1 + 2",
            "(1 + 2)",
            "1 + 2 * 3 + 4",
            "(1 + 2) * (3 + 4)",
            "\"this is a 'string' litteral\"",
            "10 + plus(1 + 2, 3)",
            "1 - ++i"
        };

//        System.out.println("===== AST PARSING =====");
//        ANTLRInputStream input = new ANTLRInputStream("Maybe A");
//        LangLexer lexer = new LangLexer(input);
//        LangParser parser = new LangParser(new CommonTokenStream(lexer));
//        System.out.println(parser.type().ast);

        for (String str : toParse) {
            ANTLRInputStream input = new ANTLRInputStream(str);
            LangLexer lexer = new LangLexer(input);
            LangParser parser = new LangParser(new CommonTokenStream(lexer));
            System.out.printf("Parsing \"%s\"\n", str.replace("\n", ""));
//            Ast ast = new LangAstVisitor().visit(parser.lang());
            System.out.println(parser.lang().ast);
            System.out.println();
        }
    }

}