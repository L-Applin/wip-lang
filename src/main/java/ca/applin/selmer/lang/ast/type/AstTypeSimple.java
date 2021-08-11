package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import java.util.Objects;

public class AstTypeSimple extends AstType {
    public String name;
    public AstTypeSimple(String name) {
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
