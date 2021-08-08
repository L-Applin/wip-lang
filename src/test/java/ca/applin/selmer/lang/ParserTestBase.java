package ca.applin.selmer.lang;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.applin.selmer.lang.LangParser.LangContext;
import ca.applin.selmer.lang.ast.Ast;
import java.util.function.Function;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class ParserTestBase {

    protected Ast getAstFor(String toParse, Function<LangParser, Ast> astTypeExtractor) {
        ANTLRInputStream input = new ANTLRInputStream(toParse);
        LangLexer lexer = new LangLexer(input);
        CommonTokenStream inputStream = new CommonTokenStream(lexer);
        LangParser parser = new LangParser(inputStream);
        Ast ast = astTypeExtractor.apply(parser);
        assertNotNull(ast);
        System.out.println(toParse);
        System.out.println(ast);
        return ast;
    }

    protected Function<LangParser, Ast> langContext= langParser -> langParser.lang().ast;
    protected Function<LangParser, Ast> typeContext = langParser -> langParser.type().ast;

}
