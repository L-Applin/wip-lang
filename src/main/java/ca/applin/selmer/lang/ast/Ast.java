package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;

public interface Ast {

    <T> T visit(AstVisitor<T> visitor);
}
