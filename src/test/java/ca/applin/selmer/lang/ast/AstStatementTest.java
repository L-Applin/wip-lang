package ca.applin.selmer.lang.ast;

import static org.junit.jupiter.api.Assertions.*;

import ca.applin.selmer.lang.ParserTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AstStatementTest extends ParserTestBase {

   @ParameterizedTest
   @DisplayName("If statement parsing")
   @ValueSource(strings = {
      """
      if x > 0 {
        funcCall(1) 
      } else {
        funcCall(2) 
      }
      """,
      """
      if x > 0 {
        funcCall(1)
      }
      """
   })
   public void ifStatementParsingTest(String toParse) {
      AstIfStatement ast = (AstIfStatement) getAstFor(toParse, stmtContext);
      assertNotNull(ast);
      assertNotNull(ast.check);
      assertNotNull(ast.thenBlock);
   }

}