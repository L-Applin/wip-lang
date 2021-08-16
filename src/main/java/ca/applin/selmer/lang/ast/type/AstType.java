package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.Ast;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

public abstract class AstType extends Ast {

    public static final AstType UNKNOWN = UnknownType.DEFAULT_INSTANCE;
    public static final AstType UNIT = UnitType.DEFAULT_INSTANCE;
    public static final AstType VOID = VoidType.DEFAULT_INSTANCE;

    public static final AstType NO_TYPE =
        new AstType(new CommonToken(-1, "<not a type>"), new CommonToken(-1, "<not a type>")) {
            @Override
            public String toString() {
                return "~~NOT_A_TYPE~~";
            }
            public <T> T visit(AstVisitor<T> visitor) {
                return visitor.visit(this);
            }
        };

    public AstType(Token start, Token stop) {
        super(start, stop);
    }

    public boolean isKnown = true;
    public boolean isStructType;
    public boolean isFuncType;

    public boolean isKnown() {
        return isKnown;
    }
}

