package ca.applin.selmer.lang.typer;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstIfStatement;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.scope.Scope;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
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

    @DisplayName("If statement should have new scope instance created for the `then` and `else` block")
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
        ast.visit(scoper);
        assertNotNull(ast.scope);
        AstVariableDeclaration varDecl = ((AstVariableDeclaration) ast.code.get(0));
        assertEquals(varDecl.scope, ast.scope);
        AstIfStatement ifStmt = ((AstIfStatement) ast.code.get(1));
        assertNotEquals(ast.scope, ifStmt.thenBlock.scope);
        assertEquals(ast.scope, ifStmt.thenBlock.scope.parent);
        if (ifStmt.elseBlock != null) {
            assertNotEquals(ast.scope, ifStmt.elseBlock.scope);
            assertEquals(ast.scope, ifStmt.elseBlock.scope.parent);
        }
    }

    @DisplayName("Nested if statement should have new scope instance created for all `then` and `else` block")
    @ParameterizedTest
    @ValueSource(strings = {
            """
                     {
                      println :: (str:String, x:Int, y:Int) -> { return 10 }
                      e: Int = 20
                      if e > 10 {
                        x := 1 + 10 + 100
                        if (e > x) { 
                          println("e > x", e, x)
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
        ast.visit(scoper);

        for (Ast stmt : ast) {
            assertEquals(ast, stmt.parent);
        }
        assertNotNull(ast.scope);
        AstVariableDeclaration varDecl = ((AstVariableDeclaration) ast.code.get(1));
        assertEquals(varDecl.scope, ast.scope);

        // first if, e > 10
        AstIfStatement ifStmt = ((AstIfStatement) ast.code.get(2));
        assertNotEquals(ast.scope, ifStmt.thenBlock.scope);
        assertEquals(ast.scope, ifStmt.thenBlock.scope.parent);
        assertNotEquals(ast.scope, ifStmt.elseBlock.scope);
        assertEquals(ast.scope, ifStmt.elseBlock.scope.parent);
        for (Ast stmt : ifStmt.thenBlock) {
            assertNotEquals(stmt.scope, ast.scope);
        }

        // nested if, e > z
        AstVariableDeclaration nestedVarDecl = (AstVariableDeclaration) ifStmt.thenBlock.code
                .get(0); // x := 1 + 10 + 100
        assertNotEquals(nestedVarDecl.scope, ast.scope);
        assertEquals(nestedVarDecl.scope, ifStmt.thenBlock.scope);
        AstIfStatement nestedIfStmt = (AstIfStatement) ifStmt.thenBlock.code.get(1);
        // println("e > z")
        AstFuncCall ifPrintFuncCall = (AstFuncCall) nestedIfStmt.thenBlock.code.get(0);
        assertNotEquals(ifPrintFuncCall.scope, ast.scope);
        assertNotEquals(ifPrintFuncCall.scope, ifStmt.scope);
        AstFuncCall elsePrintFuncCall = (AstFuncCall) nestedIfStmt.elseBlock.code.get(0);
        assertNotEquals(elsePrintFuncCall.scope, ast.scope);
        assertNotEquals(elsePrintFuncCall.scope, ifStmt.scope);

    }


    @DisplayName("Variable reference for variable declared in same scope should not cause problems")
    @ParameterizedTest
    @ValueSource(strings = {
            """
              {
                x: Int = 5
                y: Int = 37
                z: Int = x + y
              }
            """,
            """ 
              {
                x: Int
                x = 2
                x = x + 10
                y := x
                z := x + 2 - 1 * 6
              }
            """
    })
    // this test the structure of the created AST, do not change the test input code, it will make the test fail
    void varRefInScopeTest(String toParse) {
        assertDoesNotThrow(() -> {
            AstCodeBlock codeBlock = getAstFor(toParse, codeBlockContext);
            assertNull(codeBlock.scope);
            codeBlock.visit(scoper);
            assertNotNull(codeBlock.scope);
            assertTrue(codeBlock.scope.containsKey("x"));
            assertTrue(codeBlock.scope.containsKey("y"));
            assertTrue(codeBlock.scope.containsKey("z"));
        });
    }
}