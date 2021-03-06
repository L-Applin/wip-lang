package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.Token;

public class AstTypeFunction extends AstType {
    public List<AstType> args;
    public AstType ret;

    public AstTypeFunction(List<AstType> args, AstType ret, Token start, Token stop) {
        super(start, stop);
        this.args = args;
        this.ret = ret;
        this.isFuncType = true;
    }

    @Override
    public String toString() {
        return "Func:[args=(%s), return=(%s)]".formatted(
                args.stream().map(AstType::toString).collect(Collectors.joining(", ")),
                ret.toString()
        );
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
