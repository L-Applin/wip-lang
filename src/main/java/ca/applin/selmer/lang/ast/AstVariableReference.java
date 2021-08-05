package ca.applin.selmer.lang.ast;

public class AstVariableReference extends AstExpression {

    public String varName;

    public AstVariableReference(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "(VariableRef %s".formatted(varName);
    }
}
