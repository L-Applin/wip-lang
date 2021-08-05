package ca.applin.selmer.lang.ast.type;

import java.util.List;
import java.util.stream.Collectors;

public class AstTypeFunction extends AstType {
    public List<AstType> args;
    public AstType ret;

    public AstTypeFunction(List<AstType> args, AstType ret) {
        this.args = args;
        this.ret = ret;
    }

    @Override
    public String toString() {
        return "Func:[args=(%s), return=(%s)]".formatted(
                args.stream().map(AstType::toString).collect(Collectors.joining(", ")),
                ret.toString()
        );
    }
}
