package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.Ast;

public abstract class AstType implements Ast {

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

    public static final AstType NO_TYPE = new AstType() {
        @Override
        public String toString() {
            return "~~NOT_A_TYPE~~";
        }
        public <T> T visit(AstVisitor<T> visitor) {
            return visitor.visit(this);
        }
    };

    public boolean isKnown = true;
    public boolean isStructType;

    public boolean isKnown() {
        return isKnown;
    }
}

