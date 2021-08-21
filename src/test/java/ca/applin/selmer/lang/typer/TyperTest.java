package ca.applin.selmer.lang.typer;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.AstTypeSimple;
import ca.applin.selmer.lang.ast.type.IntType;
import ca.applin.selmer.lang.ast.type.UnknownType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class TyperTest extends ParserTestBase {

    private Typer typer = new Typer();

    @Test
    public void equalTypes() {
        assertEquals(IntType.INSTANCE, new AstTypeSimple("Int"));
    }

    @DisplayName("Binop type inference")
    @ParameterizedTest
    @ValueSource(strings = {
            "1 + 2",
            "(1 + 2) * (3 + 4)",
            "1 + 2 * 3 / 4 % 5 | 6"
    })
    public void testBinopIntTypeInference(String toParse) {
        AstBinop ast = (AstBinop) getAstFor(toParse, exprContext);
        assertEquals(UnknownType.DEFAULT_INSTANCE, ast.type);
        AstType typeInfered = typer.visit(ast);
        assertEquals(IntType.INSTANCE, typeInfered);
        assertEquals(IntType.INSTANCE, ast.type);
    }

    @DisplayName("Variable decl and reference to an Int expression")
    @ParameterizedTest
    @ValueSource(strings = {
        """ 
            {
              x: Int = 5
              y := x
              y = y + 1
              x = y
            } 
        """
    })
    void testVariableDecl(String toParse) {
        AstCodeBlock codeBlock = getAstFor(toParse, codeBlockContext);
        typer.visit(codeBlock);
        System.out.println(codeBlock);
    }
}