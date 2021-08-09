package ca.applin.selmer.lang.ast;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Function call expression tests")
class AstFuncCallTest extends ParserTestBase {

    private static record Pair<A, B>(A first, B second) {}

    @ParameterizedTest
    @DisplayName("Parsing function call without arguments")
    @ValueSource(strings = {
            "myFunc()~~~myFunc",
            "f()~~~f"
    })
    public void emptyFuncCall(String str) {
        Pair<String, String> split = extract(str);
        String toParse = split.first();
        AstFuncCall ast = (AstFuncCall) getAstFor(toParse, exprContext);
        assertEquals(split.second(), ast.functionName);
    }

    @ParameterizedTest
    @DisplayName("Parsing function call with a single argument passed")
    @ValueSource(strings = {
            "myFunc(myVar)~~~myFunc",
            "f(1 + 2)~~~f",
            "func_name_with_underscore(anoth_func(1, 2, s()))~~~func_name_with_underscore"
    })
    public void singleArgFunctionTest(String str) {
        Pair<String, String> split = extract(str);
        String toParse = split.first();
        AstFuncCall ast = (AstFuncCall) getAstFor(toParse, exprContext);
        assertEquals(split.second(), ast.functionName);
        assertEquals(1, ast.args.size());
    }

    @ParameterizedTest
    @DisplayName("Parsing function call with multiple arguments passed")
    @ValueSource(strings = {
            "myFunc(myVar, anotherVar)~~~myFunc",
            "f(1 + 2, 3 * 4, 5 / 6)~~~f",
            "func_name_with_underscore(f(), g(), h())~~~func_name_with_underscore"
    })
    public void multipleArgFunctionTest(String str) {
        Pair<String, String> split = extract(str);
        String toParse = split.first();
        AstFuncCall ast = (AstFuncCall) getAstFor(toParse, exprContext);
        assertEquals(split.second(), ast.functionName);
        assertTrue(ast.args.size() > 1);
    }


    private Pair<String, String> extract(String str) {
        String[] strs = str.split("~~~");
        return new Pair<>(strs[0], strs[1]);
    }

}