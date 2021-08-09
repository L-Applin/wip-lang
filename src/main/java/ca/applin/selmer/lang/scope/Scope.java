package ca.applin.selmer.lang.scope;

import ca.applin.selmer.lang.ast.type.AstType;
import java.util.Map;
import java.util.Set;

public class Scope {
    public Scope parent;
    public Map<String, AstType> knownVariables;
    public Set<AstType> knownTypes;

}
