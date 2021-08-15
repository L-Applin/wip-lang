package ca.applin.selmer.lang.typer;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.ast.AstCodeBlock;
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
    void testScopeOfIfBlock(String toParse) {
        AstCodeBlock ast = getAstFor(toParse, codeBlockContext);
        ast.visit(scoper);
        assertNotNull(ast.scope);
        AstVariableDeclaration varDecl = ((AstVariableDeclaration) ast.code.get(0));
        assertEquals(varDecl.scope, ast.scope);
        AstIfStatement ifStmt = ((AstIfStatement)ast.code.get(1));
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
              e: Int = 20
              if e > 10 {
                x := 1 + 10 + 100
                if (e > z) { 
                  println("e < z")
                } else {
                  println("e <= z")
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
    // this test the structure of the created AST, do not change the code, it will make the test fail
    void nestedIfStatements(String toParse) {
        // e: Int = 20
        AstCodeBlock ast = getAstFor(toParse, codeBlockContext);
        ast.visit(scoper);
        assertNotNull(ast.scope);
        AstVariableDeclaration varDecl = ((AstVariableDeclaration) ast.code.get(0));
        assertEquals(varDecl.scope, ast.scope);

        // first if, e > 10
        AstIfStatement ifStmt = ((AstIfStatement)ast.code.get(1));
        assertNotEquals(ast.scope, ifStmt.thenBlock.scope);
        assertEquals(ast.scope, ifStmt.thenBlock.scope.parent);
        assertNotEquals(ast.scope, ifStmt.elseBlock.scope);
        assertEquals(ast.scope, ifStmt.elseBlock.scope.parent);

        // nested if, e > z
        AstVariableDeclaration nestedVarDecl = (AstVariableDeclaration) ifStmt.thenBlock.code.get(0); // x := 1 + 10 + 100
        assertNotEquals(nestedVarDecl.scope, ast.scope);
        assertEquals(nestedVarDecl.scope, ifStmt.thenBlock.scope);
        AstIfStatement nestedIfStmt = (AstIfStatement) ifStmt.thenBlock.code.get(1);

        // println("e < z")


    }


}