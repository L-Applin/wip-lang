package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.ast.type.AstType;
import java.util.List;

public class AstFunctionDeclaration extends AstDeclaration {
    public static record AstFunctionArgs(String name, AstType type) {}
    public static record AstFunctionBody(List<Ast> code) {}
    public String name;
    public AstType type;
    public List<AstFunctionArgs> args;
    public AstFunctionBody body;

    public AstFunctionDeclaration(String name, AstType type,
            List<AstFunctionArgs> args,
            AstFunctionBody body) {
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
}
