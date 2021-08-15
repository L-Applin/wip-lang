package ca.applin.selmer.lang.ast;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.scope.Scope;
import ca.applin.selmer.lang.typer.Typer;
import ca.applin.selmer.lang.typer.UnknownTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Lambda Expression Test")
class AstLambdaExpressionTest extends ParserTestBase {

    private Typer typer;

    @BeforeEach
    public void init() {
       this.typer = new Typer();
    }

    @DisplayName("Lambda var declaration with infered types that dont have all arg type defined should throw an error")
    @ParameterizedTest
    @ValueSource(strings = {
            "lambdaTest := x -> x + 1",
            "lambdaTest := (x:Int, y) -> x + y",
            "lambdaTest := (z:Int, bla) -> x + y"
    })
    void testInferedLambdaErrorExpr(String toParse) {
        AstVariableDeclaration decl = (AstVariableDeclaration) getAstFor(toParse, declContext);
        UnknownTypeException e = assertThrows(UnknownTypeException.class, () -> decl.visit(typer));

    }

    @DisplayName("Lambda var declaration with infered types that  have all arg type defined should infer all types")
    @ParameterizedTest
    @ValueSource(strings = {
            "lambdaTest := x:int -> x + 1",
            "lambdaTest := (x:Int, y:Int) -> x + y",
    })
    void testInferedLambdaExpr(String toParse) {
        AstVariableDeclaration decl = (AstVariableDeclaration) getAstFor(toParse, declContext);
        assertDoesNotThrow( () -> decl.visit(typer));
    }


}