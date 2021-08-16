package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import java.util.Objects;
import org.antlr.v4.runtime.Token;

public class AstTypeSimple extends AstType {
    public String name;
    public AstTypeSimple(String name, Token start, Token stop) {
        super(start, stop);
        this.name = name;
    }

    public AstTypeSimple(String name) {
        super(DUMMY_TOKEN, DUMMY_TOKEN);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type:%s".formatted(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        AstTypeSimple that = (AstTypeSimple) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
