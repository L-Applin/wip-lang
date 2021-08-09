package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;

public class StringType extends AstTypeSimple {

    public static final StringType INSTANCE = new StringType();

    public StringType() {
        super("String");
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
