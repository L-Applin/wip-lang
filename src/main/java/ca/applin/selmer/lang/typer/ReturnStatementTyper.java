package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.AstBaseVisitor;
import ca.applin.selmer.lang.AstVisitor;
import ca.applin.selmer.lang.ast.AstCodeBlock;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstIfStatement;
import ca.applin.selmer.lang.ast.AstReturnExpression;
import ca.applin.selmer.lang.ast.AstStructDeclaration;
import ca.applin.selmer.lang.ast.AstTypeDeclaration;
import ca.applin.selmer.lang.ast.AstVariableDeclaration;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.AstWhileStatement;
import ca.applin.selmer.lang.ast.type.AstType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReturnStatementTyper extends AstBaseVisitor<List<AstType>> {

    private final Typer typer = new Typer();

    @Override
    public List<AstType> visit(AstReturnExpression astReturnExpression) {
        List<AstType> types = new ArrayList<>();
        types.add(astReturnExpression.expression.visit(typer));
        return types;
    }

    @Override
    public List<AstType> visit(AstCodeBlock codeBlock) {
        return new ArrayList<>(codeBlock.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList());
    }

    @Override
    public List<AstType> visit(AstIfStatement astIfStatement) {
        List<AstType> thenReturns = astIfStatement.thenBlock.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList();
        List<AstType> elseReturns = astIfStatement.elseBlock.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList();
        List<AstType> types = new ArrayList<>();
        types.addAll(thenReturns);
        types.addAll(elseReturns);
        return types;
    }

    @Override
    public List<AstType> visit(AstVariableDeclaration astVariableDeclaration) {
        return new ArrayList<>();
    }

    @Override
    public List<AstType> visit(AstWhileStatement astWhileStatement) {
        return new ArrayList<>(astWhileStatement.code.code.stream()
                .flatMap(ast -> ast.visit(this).stream())
                .toList());
    }

    @Override
    public List<AstType> visit(AstVariableReference astVariableReference) {
        List<AstType> types = new ArrayList<>();
        types.add(astVariableReference.type);
        return types;
    }

    @Override
    public List<AstType> visit(AstTypeDeclaration astTypeDeclaration) {
        return new ArrayList<>();
    }

    @Override
    public List<AstType> visit(AstStructDeclaration astStructDeclaration) {
        return new ArrayList<>();
    }

}
