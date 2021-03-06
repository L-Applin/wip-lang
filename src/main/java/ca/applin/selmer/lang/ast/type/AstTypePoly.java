package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import java.util.List;
import org.antlr.v4.runtime.Token;

public class AstTypePoly extends AstTypeSimple {

    public List<String> polyArg;

    public AstTypePoly(String name, List<String> polyArgs, Token start, Token stop) {
        super(name, start, stop);
        this.polyArg = polyArgs;
    }

    @Override
    public String toString() {
        return super.toString() + ", poly=<" + String.join(", ", polyArg) + ">";
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
