package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.AstDeclaration;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

public class AstTypeDeclaration extends AstDeclaration {
    public String name;
    public List<String> polyArgs;
    public AstType type;

    public AstTypeDeclaration(String name, AstType type, Token start, Token stop) {
        this(name, new ArrayList<>(), type, start, stop);
    }

    public AstTypeDeclaration(String name, List<String> polyArgs, AstType type, Token start, Token stop) {
        super(start, stop);
        this.name = name;
        this.polyArgs = polyArgs;
        this.type = type;
    }

    @Override
    public String toString() {
        return "TypeDecl:[name=%s, %sType=%s]".formatted(
                name,
                polyArgs.isEmpty() ? "" : "poly=<" +String.join(", ", polyArgs) + ">, ",
                type.toString()
        );
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
