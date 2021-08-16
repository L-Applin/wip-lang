package ca.applin.selmer.lang.ast.type;

import ca.applin.selmer.lang.AstVisitor;
import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
import org.antlr.v4.runtime.CommonToken;

public class StringType extends AstTypeSimple {

    public static final StringType INSTANCE = new StringType();

    public StringType() {
        super("String", new CommonToken(-1, "<string type>"), new CommonToken(-1, "<string type>"));
    }

    public <T> T visit(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
