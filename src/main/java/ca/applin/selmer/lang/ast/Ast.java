package ca.applin.selmer.lang.ast;

import java.util.Optional;

public abstract class Ast {
    public Ast next;

    public Optional<Ast> next() {
        return Optional.ofNullable(next);
    }

}
