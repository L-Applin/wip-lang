package ca.applin.selmer.lang.ast.type;

public class AstTypeSimple extends AstType {
    public String name;
    public AstTypeSimple(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type:%s".formatted(name);
    }
}
