package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.List;

// todo in parser
public class AstLambdaExpression extends AstExpression {
    public static record AstLambdaArgs(String name, AstType type) {}
    public static record AstLambdaBody(List<Ast> code) {}

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
