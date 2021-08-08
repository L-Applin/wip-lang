package ca.applin.selmer.lang.ast.type;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Testing function type parsing")
class AstTypeFunctionTest extends ParserTestBase {

    @ParameterizedTest
    @DisplayName("Single arg function types")
    @ValueSource(strings = {
            "Int -> Bool",
            "() -> Int",
            "Int -> ()",
            "[Int] -> (String, Double)",
            "A -> B -> [A] -> [B]",
            "A -> Bool -> [A] -> [A]"
    })
    public void functionTypeTest(String toParse) {
        AstTypeFunction ast = (AstTypeFunction) getAstFor(toParse, typeContext);
        assertEquals(1, ast.args.size());
        assertNotNull(ast.ret);
    }

    @ParameterizedTest
    @DisplayName("Multiple args function types")
    @ValueSource(strings = {
            "(Int, A) -> String",
            "(Int, Double) -> String"
    })
    public void functiontypeMultiple(String toParse) {
        AstTypeFunction ast = (AstTypeFunction) getAstFor(toParse, typeContext);
        assertEquals(2, ast.args.size());
        assertNotNull(ast.ret);
        assertEquals(new AstTypeSimple("Int"), ast.args.get(0));
        assertEquals(new AstTypeSimple("String"), ast.ret);
    }
}