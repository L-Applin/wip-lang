package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import org.antlr.v4.runtime.Token;

public class AstVariableDeclaration extends AstDeclaration {
    public String varName;
    public AstType type;
    public AstExpression initExpr;

    public AstVariableDeclaration(String varName, AstType type,
            AstExpression initExpr, Token start, Token stop) {
        super(start, stop);
        this.varName = varName;
        this.type = type;
        this.initExpr = initExpr;
    }

    @Override
    public String toString() {
        return "VarDecl:[name=%s type=%s init=%s]".formatted(
                varName,
                type.toString(),
                initExpr == null ? "_" : initExpr.toString()
        );
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
