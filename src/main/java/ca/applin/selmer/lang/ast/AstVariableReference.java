package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;

public class AstVariableReference extends AstExpression {

    public String varName;

    public AstVariableReference(String varName) {
        this.varName = varName;
        this.type = AstType.UNKNOWN;
    }

    @Override
    public String toString() {
        return "VariableRef:[%s type=%s]".formatted(varName, type);
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
