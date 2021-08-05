package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.ast.AstStructMemberDeclaration;
import java.util.List;

public class AstStructDeclaration extends AstType {
    public String name;
    public List<String> polyArg;
    public List<AstStructMemberDeclaration> members;

    public AstStructDeclaration(String name, List<String> polyArg,
            List<AstStructMemberDeclaration> members) {
        this.name = name;
        this.polyArg = polyArg;
        this.members = members;
    }

    @Override
    public String toString() {
        return "StructDecl:%s%s members=%s".formatted(
                name, polyArg.isEmpty() ? "" : " poly=<" + String.join(", ", polyArg) + ">",
                members.toString()
        );
    }
}
