package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.ast.Ast;

public abstract class AstType extends Ast {

    public static final AstType UNKNOWN = UnknownType.INSTANCE;

    public static final AstType UNIT = new AstType() {
        @Override
        public String toString() {
            return "Unit";
        }
    };

    public static final AstType VOID = new AstType() {
        @Override
        public String toString() {
            return "VOID";
        }
    };


    public boolean isKnown() { return true; }
}

