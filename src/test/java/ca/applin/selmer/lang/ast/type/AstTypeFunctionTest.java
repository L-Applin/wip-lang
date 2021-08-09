package ca.applin.selmer.lang.ast.type;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.type.AstSumType.SumTypeConstructor;
import java.util.List;
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
            "(Int, Double) -> String",
            "(Int, Double) -> String"
    })
    public void functiontypeMultiple(String toParse) {
        AstTypeFunction ast = (AstTypeFunction) getAstFor(toParse, typeContext);
        assertEquals(2, ast.args.size());
        assertNotNull(ast.ret);
        assertEquals(new AstTypeSimple("Int"), ast.args.get(0));
        assertEquals(new AstTypeSimple("String"), ast.ret);
    }

    @ParameterizedTest
    @DisplayName("Sum types, ")
    @ValueSource(strings = {
            "First | Second",
            "First A | Second",
            "First A | Second B",
            "First A | Second B | Third C | fourth D",
            "First A | Second B C D"
    })
    public void sumTypesTest(String toParse) {
        AstSumType ast = (AstSumType) getAstFor(toParse, typeContext);
        List<SumTypeConstructor> cons = ast.constructors;
        assertFalse(cons.isEmpty());
        assertEquals("First", cons.get(0).constructorName());
        assertEquals("Second", cons.get(1).constructorName());
    }


}