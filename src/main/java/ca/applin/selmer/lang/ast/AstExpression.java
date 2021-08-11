package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.type.AstType;

public abstract class AstExpression implements Ast {
    public AstType type;
    public boolean isReturn;

    public AstExpression() {
        this.type = AstType.UNKNOWN;
    }

}
