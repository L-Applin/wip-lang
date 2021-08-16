package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import org.antlr.v4.runtime.Token;

public class AstVariableReference extends AstExpression {

    public String varName;

    public AstVariableReference(String varName, Token start, Token stop) {
        super(start, stop);
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
