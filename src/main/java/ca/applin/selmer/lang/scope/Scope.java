package ca.applin.selmer.lang.scope;

import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.antlr.v4.runtime.misc.NotNull;

public class Scope {
    public Scope parent;
    public Map<String, AstVariableDeclaration> knownVariables;
    public Map<String, AstFunctionDeclaration> knownFunc;
    public Set<AstType> knownTypes;

    public Scope() {
        knownVariables = new HashMap<>();
        knownTypes = new HashSet<>();
        knownFunc = new HashMap<>();
    }

    public AstVariableDeclaration getVarByName(String name) {
        return knownVariables.get(name);
    }

    public @NotNull AstVariableDeclaration getVarByNameOrThrow(String name, RuntimeException e) {
        AstVariableDeclaration decl =knownVariables.get(name);
        if (decl == null) {
            throw e;
        }
        return decl;
    }

    public boolean isAlreadyKnown(String name) {
        return knownVariables.containsKey(name) || knownFunc.containsKey(name);
    }


    public AstType findTypeFor(String identifier) {
        AstVariableDeclaration decl = knownVariables.get(identifier);
        if (decl != null) {
            return decl.type;
        }
        if (parent != null) {
            return parent.findTypeFor(identifier);
        }
        return AstType.UNKNOWN;
    }

    public boolean containsKey(String key) {
        if (knownVariables.containsKey(key)) return true;
        if (parent != null)                  return parent.containsKey(key);
        return false;
    }

}
