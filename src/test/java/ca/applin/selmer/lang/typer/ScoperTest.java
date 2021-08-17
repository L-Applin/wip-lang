package ca.applin.selmer.lang.typer;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstIfStatement;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Test the creating of Scope instance correctly in the Ast")
class ScoperTest extends ParserTestBase {

    private Scoper scoper;

    @BeforeEach
    void init() {
        scoper = new Scoper();
    }

    @DisplayName("If statements should recognize variables declared in parent scope")
    @ParameterizedTest
    @ValueSource(strings = {
            """
            {
              e: Int = 20
              if e > 10 {
                y : Int = 10
                e = e + y 
              }
            }
            """,
            """
             {
              e: Int = 20.0
              if e > 10.0 {
                y : Int = 10
                e = e + y 
              } else {
                z : Int = 30
                e = e + z 
              } 
            }       
            """

    })
        // this test the common structures of the created ASTs, do not change the test input code, it will make the test fail
    void testScopeOfIfBlock(String toParse) {
        AstCodeBlock ast = getAstFor(toParse, codeBlockContext);
        assertDoesNotThrow( () -> ast.visit(scoper));
        AstIfStatement ifStmt = ((AstIfStatement) ast.code.get(1));
        assertTrue(ifStmt.scope.containsId("e"));
        assertTrue(ifStmt.check.scope.containsId("e"));
        assertTrue(ifStmt.thenBlock.scope.containsId("e"));
        assertTrue(ifStmt.thenBlock.code.get(1).scope.containsId("y"));
        if (ifStmt.elseBlock != null) {
            assertTrue(ifStmt.elseBlock.scope.containsId("e"));
        }
    }

    @DisplayName("Nested if statement should have reference to all variables declared in all parent scopes")
    @ParameterizedTest
    @ValueSource(strings = {
            """
             {
              println :: (str:String, x:Int, y:Int) -> { return 10 }
              e: Int = 20
              if e > 10 {
                x := 1 + 10 + 100
                if (e > x) { 
                  z : Bool = e > x
                  println("z :? e > x", z, e, x)
                } else {
                  println("e <= x", e, x)
                }
                y : Int = 10
                e = e + y 
              } else {
                z : Int = 30
                e = e + z
              } 
            }       
            """
    })
    // this test the structure of the created AST, do not change the test input code, it will make the test fail
    void nestedIfStatements(String toParse) {
        // e: Int = 20
        AstCodeBlock ast = getAstFor(toParse, codeBlockContext);
        assertDoesNotThrow( () -> ast.visit(scoper));

        AstVariableDeclaration eVarDecl = (AstVariableDeclaration) ast.code.get(1);
        assertTrue(eVarDecl.scope.containsId("println"));
        AstIfStatement firstIf = (AstIfStatement) ast.code.get(2);
        assertTrue(firstIf.scope.containsId("e"));
        AstCodeBlock firstIfThen = firstIf.thenBlock;
        firstIfThen.forEach(a -> assertTrue(a.scope.containsId("e")));
        firstIfThen.forEach(a -> assertTrue(a.scope.containsId("println")));

        AstIfStatement nestedIf = ((AstIfStatement) firstIfThen.code.get(1));
        nestedIf.thenBlock.forEach(a -> assertTrue(a.scope.containsId("e")));
        nestedIf.thenBlock.forEach(a -> assertTrue(a.scope.containsId("println")));
        nestedIf.elseBlock.forEach(a -> assertTrue(a.scope.containsId("println")));

        assertTrue(nestedIf.thenBlock.code.get(0).scope.containsId("x"));
        assertTrue(nestedIf.thenBlock.code.get(1).scope.containsId("z"));
        assertTrue(nestedIf.elseBlock.code.get(0).scope.containsId("x"));
        assertFalse(nestedIf.elseBlock.code.get(0).scope.containsId("z"));
        firstIfThen.code.get(3).scope.containsId("y");

        AstCodeBlock firstIfElse = firstIf.elseBlock;
        firstIfElse.code.forEach(a -> assertTrue(a.scope.containsId("e")));
        firstIfElse.code.forEach(a -> assertTrue(a.scope.containsId("println")));
        firstIfElse.code.forEach(a -> assertFalse(a.scope.containsId("x")));
        assertTrue(firstIfElse.code.get(1).scope.containsId("z"));

    }


    @DisplayName("Variable reference for variable declared in same scope should not cause problems")
    @ParameterizedTest
    @ValueSource(strings = {
            """
              {
                x: Int = 5
                y: Int = 37
                z: Int = x + y
                zz: Int = x + y + z
              }
            """
    })
    // this test the structure of the created AST, do not change the test input code, it will make the test fail
    void varRefInScopeTest(String toParse) {
        AstCodeBlock codeBlock = getAstFor(toParse, codeBlockContext);
        assertDoesNotThrow(() -> {
            codeBlock.visit(scoper);
            assertNotNull(codeBlock.scope);
        });

        assertFalse(codeBlock.scope.containsId("x"));
        assertFalse(codeBlock.scope.containsId("y"));
        assertFalse(codeBlock.scope.containsId("z"));
        assertFalse(codeBlock.scope.containsId("zz"));

        assertTrue(codeBlock.code.get(0).scope.containsId("x"));
        assertFalse(codeBlock.code.get(0).scope.containsId("y"));
        assertFalse(codeBlock.code.get(0).scope.containsId("z"));
        assertFalse(codeBlock.code.get(0).scope.containsId("zz"));

        assertTrue(codeBlock.code.get(1).scope.containsId("x"));
        assertTrue(codeBlock.code.get(1).scope.containsId("y"));
        assertFalse(codeBlock.code.get(1).scope.containsId("z"));
        assertFalse(codeBlock.code.get(1).scope.containsId("zz"));

        assertTrue(codeBlock.code.get(2).scope.containsId("x"));
        assertTrue(codeBlock.code.get(2).scope.containsId("y"));
        assertTrue(codeBlock.code.get(2).scope.containsId("z"));
        assertFalse(codeBlock.code.get(2).scope.containsId("zz"));

        assertTrue(codeBlock.code.get(3).scope.containsId("x"));
        assertTrue(codeBlock.code.get(3).scope.containsId("y"));
        assertTrue(codeBlock.code.get(3).scope.containsId("z"));
        assertTrue(codeBlock.code.get(3).scope.containsId("zz"));
    }

    @DisplayName("Missing variable references should throw an exception")
    @ParameterizedTest
    @ValueSource(strings = {
            "{ x: Int = y + 2 }",
            "{ x := y }",
            """
              {
                x: Int = 12
                z := 42
                y := x + y + z
              }
            """,
            """
              {
                x := 12
                if x > 10 { 
                  x = y + z
                }
              }
            """

    })
    void scoperErrorTest(String toParse) {
        UnkownVariableReferenceException e = assertThrows(UnkownVariableReferenceException.class,
                () -> scoper.visit(getAstFor(toParse, codeBlockContext)));
        assertEquals("y", e.varRef.varName);
    }
}