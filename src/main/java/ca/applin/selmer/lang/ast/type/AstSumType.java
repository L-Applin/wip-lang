package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import java.util.List;

public class AstSumType extends AstType {
    public static record SumTypeConstructor(String constructorName, List<AstType> args) {
        @Override
        public String toString() {
            return "cons:%s%s"
                .formatted(constructorName, args == null ? "" : " args:" + args.toString());
        }
    }

    public List<SumTypeConstructor> constructors;

    public AstSumType(List<SumTypeConstructor> constructors) {
        this.constructors = constructors;
    }

    @Override
    public String toString() {
        return "SumType:%s".formatted(constructors.toString());
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}