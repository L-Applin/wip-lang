package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public abstract class Ast {

    public abstract <T> T visit(AstVisitor<T> visitor);
}
