package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.Ast;

public abstract class AstType extends Ast {

    public static final AstType UNKNOWN = UnknownType.INSTANCE;

    public static final AstType UNIT = new AstType() {
        @Override
        public String toString() {
            return "Unit";
        }
        public <T> T visit(AstVisitor<T> visitor) {
            return visitor.visit(this);
        }

    };

    public static final AstType VOID = new AstType() {
        @Override
        public String toString() {
            return "VOID";
        }
        public <T> T visit(AstVisitor<T> visitor) {
            return visitor.visit(this);
        }

    };

    public boolean isStructType;

    public boolean isKnown() { return true; }

}

