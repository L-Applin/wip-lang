package ca.applin.selmer.lang.ast;

import java.util.Optional;

public abstract class AstExpression extends Ast {
    public Ast next;

    @Override
    public Optional<Ast> next() {
        return Optional.ofNullable(next);
    }

}
