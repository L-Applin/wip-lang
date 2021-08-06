package ca.applin.selmer.lang.ast;

import java.util.List;

public class AstCodeBlock extends AstStatement {
    public List<Ast> code;

    public AstCodeBlock(List<Ast> code) {
        this.code = code;
    }
}
