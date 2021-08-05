package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.ast.Ast;

public abstract class AstType extends Ast {
    public static final AstType UNKNOWN = new AstType() {
        @Override
        public boolean isKnown() {
            return false;
        }

        @Override
        public String toString() {
            return "<UNKNOWN/>";
        }
    };

    public static final AstType UNIT = new AstType() {
        @Override
        public String toString() {
            return "()";
        }
    };

    public boolean isKnown() { return true; }
}

