package ca.applin.selmer.lang.typer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstReturnExpression;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReturnStatementTyperTest extends ParserTestBase {

    private final ReturnStatementFinder typer = new ReturnStatementFinder();

    @ParameterizedTest
    @ValueSource(strings = {
            "fun :: Unit -> Int = () -> { return 1 }",
            "fun :: Int -> Int = i -> { return i + 2 }",
            """
            fun :: Unit -> Int = () -> {
              f :: Int -> Int = x -> x + 1
              if 1 > 0 {
                myVar: String = "will be ignored"
              } else {
                return f(10)
              }
            }
            """,
            """
            fun :: Unit -> Int = () -> {
              f :: Int -> Int -> Int = x -> y -> x + y 
              f(x -> {
                doSomeStuff := 12
                return doSomeStuff + 30
              })
              if 1 > 0 {
                myVar: String = "will be ignored"
              } else {
                return f(x -> x + 10)
              }
            }
            """
    })
    @DisplayName("Single return statement")
    public void testFindSingleReturnStatements(String toParse) {
        AstFunctionDeclaration ast = (AstFunctionDeclaration) getAstFor(toParse, funcDeclContext);
        List<AstReturnExpression> types = ast.visit(typer);
        assertEquals(1, types.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "fun :: Int -> Int = x -> { if x>1 { return 1 } else { return 2 } }",
            "fun :: Int -> Int = x -> { if x>1 { return 1 + 2} else { return f(10) } }",
            """
            fun :: Unit -> Int = x -> {
              f :: Int -> Int Int = x -> y -> x + y
              if 1 > 0 {
                myVar: String = "will be ignored"
                return myVar
              } else {
                return f(y -> { if v > 10 { return 1 } else { return 2 } })
              }
            }
            """
    })
    @DisplayName("Single return statement")
    public void testFindReturnStatements(String toParse) {
        AstFunctionDeclaration ast = (AstFunctionDeclaration) getAstFor(toParse, funcDeclContext);
        List<AstReturnExpression> types = ast.visit(typer);
        assertEquals(2, types.size());
    }

}