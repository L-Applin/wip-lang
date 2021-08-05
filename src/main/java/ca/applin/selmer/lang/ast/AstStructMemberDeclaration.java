package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.ast.type.AstType;

public class AstStructMemberDeclaration extends AstVariableDeclaration {

    public AstStructMemberDeclaration(String varName, AstType type,
            AstExpression initExpr) {
        super(varName, type, initExpr);
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
