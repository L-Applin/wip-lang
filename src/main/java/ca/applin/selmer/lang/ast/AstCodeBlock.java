package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import java.util.List;

public class AstCodeBlock extends AstStatement {
    public List<Ast> code;

    public AstCodeBlock(List<Ast> code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CodeBlock:" + code.toString();
    }

    @Override
    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
