package ca.applin.selmer.lang.ast;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration.AstFunctionArgs;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration.AstFunctionBody;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.AstTypeFunction;
import ca.applin.selmer.lang.ast.type.AstTypeSimple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Parsing of function declaration")
class AstFunctionDeclarationTest extends ParserTestBase  {

    @ParameterizedTest
    @DisplayName("Fucntions with a single argument and a single expr body")
    @ValueSource(strings = {
            "isEven :: Int -> Bool = x:Int -> { return x % 2 == 0 }",
            "isEven :: Int -> Bool = (x:Int) -> x % 2 == 0",
            "isEven :: Int -> Bool = x:Int -> x % 2 == 0",
            "isEven :: x:Int -> x % 2 == 0",
            "isEven :: Int -> Bool = x -> { return x % 2 == 0 }",
            "isEven :: Int -> Bool = (x) -> x % 2 == 0",
            "isEven :: Int -> Bool = x -> x % 2 == 0",
            "isEven :: x -> x % 2 == 0"
    })
    void functionDeclarationParsingTest(String toParse) {
        AstFunctionDeclaration ast = (AstFunctionDeclaration) getAstFor(toParse, funcDeclContext);
        assertEquals("isEven", ast.name);
        assertEquals(1, ast.args.size());
        AstFunctionArgs arg = ast.args.get(0);
        assertEquals("x", arg.name());
        if (arg.type().isKnown()) {
            assertEquals(new AstTypeSimple("Int"), arg.type());
        } else {
            assertEquals(AstType.UNKNOWN, arg.type());
        }
        if (ast.type.isKnown()) {
            AstTypeFunction funcType = (AstTypeFunction) ast.type;
            assertEquals(1, funcType.args.size());
            AstType argType = funcType.args.get(0);
            assertEquals(new AstTypeSimple("Int"), argType);
            assertEquals(new AstTypeSimple("Bool"), funcType.ret);
        } else {
            assertEquals(AstType.UNKNOWN, ast.type);
        }

        AstFunctionBody body = ast.body;
        assertEquals(1, body.code().size());
        AstExpression expr = (AstExpression) body.code().get(0);
        assertNotNull(expr);
    }

    @ParameterizedTest
    @DisplayName("Fucntions with a multiple arguments and a single expr body")
    @ValueSource(strings = {
            "areEven :: (Int, Double) -> Bool = (x:Int, y:Double) -> { return (x+y) % 2 == 0 }",
            "areEven :: (Int, Double) -> Bool = (x: Int, y: Double) -> (x+y) % 2 == 0",
            "areEven :: (x:Int, y:Double) -> (x+y) % 2 == 0",
            "areEven :: (Int, Double) -> Bool = (x, y) -> { return (x+y) % 2 == 0 }",
            "areEven :: (Int, Double) -> Bool = (x, y) -> (x+y) % 2 == 0",
            "areEven :: x -> x % 2 == 0"
    })
    public void multipleArgFunctions(String toParse) {
        AstFunctionDeclaration ast = (AstFunctionDeclaration) getAstFor(toParse, funcDeclContext);
        assertEquals("areEven", ast.name);
        if (ast.type.isKnown()) {
            AstTypeFunction funType = (AstTypeFunction) ast.type;
            assertEquals(2, ast.args.size());
            assertEquals(new AstTypeSimple("Int"), funType.args.get(0));
            assertEquals(new AstTypeSimple("Double"), funType.args.get(1));
        } else {
            assertEquals(AstType.UNKNOWN, ast.type);
        }
    }

    @ParameterizedTest
    @DisplayName("Functions with multiple code lines should parse correctly")
    @ValueSource(strings = {
            """
            myFunc :: (Bool, Int) -> String 
            = (b, i) -> {
              if (b) {
                return i + 1
              } else {
                return i
              }
            }
            """
    })
    public void functionBodyMultipleCode(String toParse) {
        AstFunctionDeclaration ast = (AstFunctionDeclaration) getAstFor(toParse, funcDeclContext);
        assertEquals("myFunc", ast.name);
        AstTypeFunction type = (AstTypeFunction) ast.type;
        assertEquals(new AstTypeSimple("Bool"), type.args.get(0));
        assertEquals(new AstTypeSimple("Int"), type.args.get(1));
        assertEquals(new AstTypeSimple("String"), type.ret);
    }
}