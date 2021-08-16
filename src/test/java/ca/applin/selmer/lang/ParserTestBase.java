package ca.applin.selmer.lang;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.applin.selmer.lang.LangParser.LangContext;
import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstDeclaration;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstStatement;
import ca.applin.selmer.lang.ast.type.AstType;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public class ParserTestBase {

    protected TestInfo testInfo;

    @BeforeEach
    public void init(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    protected <T extends Ast> T getAstFor(String toParse, Function<LangParser, T> astTypeExtractor) {
        ANTLRInputStream input = new ANTLRInputStream(toParse);
        input.name = testInfo.getTestMethod()
                .map(Method::getName).orElse("<test suite method>");
        LangLexer lexer = new LangLexer(input);
        CommonTokenStream inputStream = new CommonTokenStream(lexer);
        LangParser parser = new LangParser(inputStream);
        T ast = astTypeExtractor.apply(parser);
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
    protected Function<LangParser, AstType> typeContext = langParser -> langParser.type().ast;
    protected Function<LangParser, AstExpression> exprContext = langParser -> langParser.expr().ast;
    protected Function<LangParser, AstStatement> stmtContext = langParser -> langParser.stmt().ast;
    protected Function<LangParser, Ast> declContext = langParser -> langParser.decl().ast;
    protected Function<LangParser, AstFunctionDeclaration> funcDeclContext = langParser -> langParser.funcDecl().ast;
    protected Function<LangParser, AstCodeBlock> codeBlockContext = langParser -> langParser.codeBlock().ast;


}
