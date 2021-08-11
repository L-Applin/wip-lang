package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.List;

public class AstLambdaExpression extends AstExpression {
    public static record AstLambdaArgs(String name, AstType type) {}

    public List<AstLambdaArgs> args;
    public AstCodeBlock body;

    public AstLambdaExpression(
            List<AstLambdaArgs> args, AstCodeBlock body) {
        this.args = args;
        this.body = body;
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LambdaExpression[args=%s body=%s]".formatted(
                args.toString(), body.toString());
    }
}
