package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.List;

public class AstFuncCall extends AstExpression {
    public AstExpression base;
    public List<AstExpression> args;

    public AstFuncCall(AstExpression base, List<AstExpression> args) {
        this.base = base;
        this.args = args;
        this.type = AstType.UNKNOWN;
    }

    @Override
    public String toString() {
        return "(FunctionCall '%s' args=%s)".formatted(
                base,
                args.stream().map(AstExpression::toString).toList()
        );
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
