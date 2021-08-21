package ca.applin.selmer.lang.scope;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.typer.Scoper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScopeTest extends ParserTestBase {

    public Scoper scoper;

    @BeforeEach
    public void init() {
        scoper = new Scoper();
    }

    @Test
    public void testKnowsFuncArg() {
        String toParse =
          """
          println :: String -> String = str -> { "prinln:" + str }
          """;
        AstFunctionDeclaration funcDecl = getAstFor(toParse, funcDeclContext);
        assertDoesNotThrow(() -> funcDecl.visit(scoper));
    }

}