package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AstFuncCall extends AstExpression {
    public String functionName;
    public List<AstExpression> args;

    public AstFuncCall(String functionName,
            List<AstExpression> args) {
        this.functionName = functionName;
        this.args = args;
        this.type = AstType.UNKNOWN;
    }

    @Override
    public String toString() {
        return "(FunctionCall '%s' args=%s)".formatted(
                functionName,
                args.stream().map(AstExpression::toString).toList()
        );
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
