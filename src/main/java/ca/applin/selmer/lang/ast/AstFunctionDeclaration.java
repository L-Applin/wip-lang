package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.List;
import org.antlr.v4.runtime.Token;

public class AstFunctionDeclaration extends AstDeclaration {
    public static record AstFunctionArgs(String name, AstType type) {}
    public String name;
    public AstType type;
    public List<AstFunctionArgs> args;
    public AstCodeBlock body;

    public AstFunctionDeclaration(String name, AstType type,
            List<AstFunctionArgs> args,
            AstCodeBlock body, Token start, Token stop) {
        super(start, stop);
        this.name = name;
        this.type = type;
        this.args = args;
        this.body = body;
    }

    @Override
    public String toString() {
        return "AstFuncDecl:[name=%s type=%s args=%s code=%s]".formatted(
                name,
                type.toString(),
                args.toString(),
                body.toString()
        );
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
