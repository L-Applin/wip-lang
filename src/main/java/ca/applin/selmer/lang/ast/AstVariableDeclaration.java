package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.ast.type.AstType;

public class AstVariableDeclaration extends AstDeclaration {
    public String varName;
    public AstType type;
    public AstExpression initExpr;

    public AstVariableDeclaration(String varName, AstType type,
            AstExpression initExpr) {
        this.varName = varName;
        this.type = type;
        this.initExpr = initExpr;
    }

    @Override
    public String toString() {
        return "VarDecl:%s type=%s init=%s".formatted(
                varName,
                type.toString(),
                initExpr == null ? "_" : initExpr.toString()
        );
    }
}
