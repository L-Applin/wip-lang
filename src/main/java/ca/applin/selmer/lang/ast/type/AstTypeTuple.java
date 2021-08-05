package ca.applin.selmer.lang.ast.type;

import java.util.List;
import java.util.stream.Collectors;

public class AstTypeTuple extends AstType {

    List<AstType> types;

    public AstTypeTuple(List<AstType> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Tuple:(%s)".formatted(
            types.stream().map(AstType::toString).collect(Collectors.joining(", ")));
    }
}
