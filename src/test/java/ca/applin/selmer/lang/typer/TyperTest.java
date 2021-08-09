package ca.applin.selmer.lang.typer;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.IntType;
import ca.applin.selmer.lang.ast.type.UnknownType;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TyperTest extends ParserTestBase {

    private Typer typer = new Typer();

    @Before
    public void init() {
        this.typer = new Typer();
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "1 + 2",
            "(1 + 2) * (3 + 4)",
            "1 + 2 * 3 / 4 % 5 | 6"
    })
    public void testBinopIntTypeInference(String toParse) {
        AstBinop ast = (AstBinop) getAstFor(toParse, exprContext);
        assertEquals(UnknownType.INSTANCE, ast.type);
        AstType typeInfered = typer.visit(ast);
        assertEquals(IntType.INSTANCE, typeInfered);
        assertEquals(IntType.INSTANCE, ast.type);
    }
}