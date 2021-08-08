package ca.applin.selmer.lang.ast.type;

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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AstTypeSimple that = (AstTypeSimple) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
