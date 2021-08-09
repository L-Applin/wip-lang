package ca.applin.selmer.lang;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.applin.selmer.lang.LangParser.LangContext;
import ca.applin.selmer.lang.ast.Ast;
import java.util.List;
import java.util.function.Function;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

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

    protected List<String> tokenRead(String toParse) {
        ANTLRInputStream input = new ANTLRInputStream(toParse);
        LangLexer lexer = new LangLexer(input);
        CommonTokenStream inputStream = new CommonTokenStream(lexer);
        inputStream.fill();
        return inputStream.getTokens().stream().map(Token::getText).toList();
    }

    protected Function<LangParser, Ast> langContext= langParser -> langParser.lang().ast;
    protected Function<LangParser, Ast> typeContext = langParser -> langParser.type().ast;
    protected Function<LangParser, Ast> funcDeclContext = langParser -> langParser.funcDecl().ast;
    protected Function<LangParser, Ast> exprContext = langParser -> langParser.expr().ast;
    protected Function<LangParser, Ast> codeBlockContext = langParser -> langParser.codeBlock().ast;


}
