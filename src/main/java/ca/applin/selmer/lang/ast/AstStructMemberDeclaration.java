package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.ast.type.AstType;
import org.antlr.v4.runtime.Token;

public class AstStructMemberDeclaration extends AstVariableDeclaration {

    public AstStructMemberDeclaration(String varName, AstType type,
            AstExpression initExpr, Token start, Token stop) {
        super(varName, type, initExpr, start, stop);
    }

    @Override
    public String toString() {
        return "StructMember:%s type=%s init=%s".formatted(
                varName,
                type.toString(),
                initExpr == null ? "_" : initExpr.toString()
        );
    }

}
