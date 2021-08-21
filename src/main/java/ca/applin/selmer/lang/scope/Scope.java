package ca.applin.selmer.lang.scope;

import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstLambdaExpression.AstLambdaArgs;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.antlr.v4.runtime.misc.NotNull;

public class Scope {
    private static int id_gen = 0;
    public Scope parent;
    public Map<String, AstVariableDeclaration> knownVariables;
    public Map<String, AstFunctionDeclaration> knownFunc;
    public Map<String, AstLambdaArgs> knownLambdaArgs;
    public Map<String, AstFunctionDeclaration.AstFunctionArgs> knownFuncArgs;
    public Set<AstType> knownTypes;

    public int id;

    public Scope() {
        knownVariables = new HashMap<>();
        knownTypes = new HashSet<>();
        knownFunc = new HashMap<>();
        knownLambdaArgs = new HashMap<>();
        knownFuncArgs = new HashMap<>();
        id = id_gen++;
    }

    public Scope(Scope parent) {
        this();
        this.parent = parent;
    }

    public void addFuncDecl(AstFunctionDeclaration funcDecl) {
        knownFunc.put(funcDecl.name, funcDecl);
        funcDecl.args.forEach(arg -> funcDecl.body.scope.knownFuncArgs.put(arg.name(), arg));
    }

    public boolean containsVar(String varName) {
        if (knownVariables.containsKey(varName) || knownFunc.containsKey(varName)) {
            return true;
        }
        if (parent == null) {
            return false;
        }
        return parent.containsVar(varName);
    }

    public AstVariableDeclaration getVarByName(String name) {
        return knownVariables.get(name);
    }

    public @NotNull AstVariableDeclaration getVarByNameOrThrow(String name, RuntimeException e) {
        AstVariableDeclaration decl = knownVariables.get(name);
        if (decl == null) {
            throw e;
        }
        return decl;
    }

    public boolean areAllKnow(String... name) {
        return Stream.of(name)
                .map(this::knowsId)
                .reduce(Boolean.TRUE, Boolean::logicalAnd);
    }

    private boolean knowsId(String name) {
        return knownVariables.containsKey(name)
                || knownFunc.containsKey(name)
                || knownFuncArgs.containsKey(name)
                || knownLambdaArgs.containsKey(name)
                ;
    }



    public AstType findTypeForVar(String identifier) {
        AstVariableDeclaration decl = knownVariables.get(identifier);
        if (decl != null) {
            return decl.type;
        }
        if (parent != null) {
            return parent.findTypeForVar(identifier);
        }
        return AstType.UNKNOWN;
    }

    public boolean containsId(String key) {
        if (knowsId(key)) return true;
        if (parent != null)  return parent.containsId(key);
        return false;
    }

    @Override
    public String toString() {
        return "Scope:%d".formatted(id);
    }
}
