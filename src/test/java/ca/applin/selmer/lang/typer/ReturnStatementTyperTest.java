package ca.applin.selmer.lang.typer;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReturnStatementTyperTest extends ParserTestBase {

    private final ReturnStatementTyper typer = new ReturnStatementTyper();

    @ParameterizedTest
    @ValueSource(strings = {
            "{ return 1 }",
            "{ return 1 + 2 }",
            """
            {
              if 1 > 0 {
                myVar: String = "will be ignored"
                return 1
              } else {
                return f()
              }
            }
            """
    })
    public void testFindReturnStatements(String toParse) {
        AstCodeBlock ast = (AstCodeBlock) getAstFor(toParse, codeBlockContext);
        List<AstType> types = ast.visit(typer);
        System.out.println(types);
    }
}