package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.ast.Ast;

public interface AstType extends Ast {
    AstType UNKNOWN = new AstType() {
        @Override
        public boolean isKnown() {
            return false;
        }

        @Override
        public String toString() {
            return "<UNKNOWN>";
        }
    };

    AstType UNIT = new AstType() {
        @Override
        public String toString() {
            return "()";
        }
    };

    default boolean isKnown() { return true; }
}

