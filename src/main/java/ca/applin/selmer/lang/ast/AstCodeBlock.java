package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.v4.runtime.Token;

public class AstCodeBlock extends AstStatement implements Iterable<Ast> {

    public static AstCodeBlock empty(Token start, Token stop) {
        return new AstCodeBlock(new ArrayList<>(), start, stop);
    }

    public List<? extends Ast> code;

    public AstCodeBlock(List<? extends Ast> code, Token start, Token stop) {
        super(start, stop);
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

    @Override
    public Iterator<Ast> iterator() {
        return (Iterator<Ast>) code.iterator();
    }
}
