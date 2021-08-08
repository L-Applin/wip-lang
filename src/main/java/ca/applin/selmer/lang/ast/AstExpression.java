package ca.applin.selmer.lang.ast;

import ca.applin.selmer.lang.ast.type.AstType;
import java.util.Optional;

public abstract class AstExpression extends Ast {
    public AstType type;
    public boolean isReturn;
}
