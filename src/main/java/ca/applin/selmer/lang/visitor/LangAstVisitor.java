package ca.applin.selmer.lang.visitor;

import ca.applin.selmer.lang.LangBaseVisitor;
import ca.applin.selmer.lang.LangParser.ArrayTypeContext;
import ca.applin.selmer.lang.LangParser.BinopPlusMinusExprContext;
import ca.applin.selmer.lang.LangParser.BinopTimesDivExprContext;
import ca.applin.selmer.lang.LangParser.ExprContext;
import ca.applin.selmer.lang.LangParser.FuncCallExprContext;
import ca.applin.selmer.lang.LangParser.FuncManyTypeContext;
import ca.applin.selmer.lang.LangParser.FuncOneTypeContext;
import ca.applin.selmer.lang.LangParser.GenericTypeContext;
import ca.applin.selmer.lang.LangParser.LitteralContext;
import ca.applin.selmer.lang.LangParser.ParenExprContext;
import ca.applin.selmer.lang.LangParser.PostUnopExprContext;
import ca.applin.selmer.lang.LangParser.PreUnopExprContext;
import ca.applin.selmer.lang.LangParser.SimpleTypeContext;
import ca.applin.selmer.lang.LangParser.TupleTypeContext;
import ca.applin.selmer.lang.LangParser.TypeContext;
import ca.applin.selmer.lang.LangParser.TypeDeclContext;
import ca.applin.selmer.lang.LangParser.UnitContext;
import ca.applin.selmer.lang.LangParser.VarDeclContext;
import ca.applin.selmer.lang.LangParser.VarRefExprContext;
import ca.applin.selmer.lang.ast.Ast;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstNumLitteral;
import ca.applin.selmer.lang.ast.AstNumLitteral.NumberType;
import ca.applin.selmer.lang.ast.AstStringLitteral;
import ca.applin.selmer.lang.ast.AstUnop;
import ca.applin.selmer.lang.ast.AstUnop.UnopType;
import ca.applin.selmer.lang.ast.AstVariableReference;
import ca.applin.selmer.lang.ast.Operator;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.AstTypeArray;
import ca.applin.selmer.lang.ast.type.AstTypeDeclaration;
import ca.applin.selmer.lang.ast.type.AstTypeFunction;
import ca.applin.selmer.lang.ast.type.AstTypePoly;
import ca.applin.selmer.lang.ast.type.AstTypeSimple;
import ca.applin.selmer.lang.ast.type.AstTypeTuple;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class LangAstVisitor extends LangBaseVisitor<Ast> {


    /* ******************* *
     *        TYPES        *
     * ******************* */
    // region Types
    @Override
    public AstType visitFuncOneType(FuncOneTypeContext ctx) {
        AstType arg = (AstType) visit(ctx.type(0));
        AstType ret = (AstType) visit(ctx.type(1));
        return new AstTypeFunction(new ArrayList<>(){{ add(arg); }}, ret);
    }

    @Override
    public AstType visitFuncManyType(FuncManyTypeContext ctx) {
        List<AstType> args = ctx.typeList().type().stream()
                .map(ct -> (AstType) visit(ct))
                .toList();
        AstType ret = (AstType) visit(ctx.type());
        return new AstTypeFunction(args, ret);
    }

    @Override
    public AstType visitSimpleType(SimpleTypeContext ctx) {
        String baseType = ctx.ID().getText();
        return new AstTypeSimple(baseType);
    }

    @Override
    public AstType visitGenericType(GenericTypeContext ctx) {
        String base = ctx.simpleType().getText();
        List<String> gens = ctx.ID().stream().map(ParseTree::getText).toList();
        return new AstTypePoly(base, gens);
    }

    @Override
    public AstType visitUnit(UnitContext ctx) {
        return AstType.UNIT;
    }

    @Override
    public AstType visitTupleType(TupleTypeContext ctx) {
        List<TypeContext> nodes = ctx.typeList().type();
        List<AstType> types = nodes.stream()
                .map(ct -> (AstType) visit(ct))
                .toList();
        return new AstTypeTuple(types);
    }

    @Override
    public AstType visitArrayType(ArrayTypeContext ctx) {
        return new AstTypeArray((AstType) visit(ctx.type()));
    }
    // endregion

    /* ******************* *
     *    DECLARATIONS     *
     * ******************* */
    // region declaration
    @Override
    public AstTypeDeclaration visitTypeDecl(TypeDeclContext ctx) {
        GenericTypeContext gct = ctx.genericType();
        if (gct == null) {
            SimpleTypeContext stc = ctx.simpleType();
            AstType type = (AstType) visit(ctx.type());
            String name = stc.ID().getText();
            return new AstTypeDeclaration(name, new ArrayList<>(), type);
        }
        String base = gct.simpleType().getText();
        List<String> polyArg = gct.ID().stream().map(TerminalNode::getText).toList();
        AstType type = (AstType) visit(ctx.type());
        return new AstTypeDeclaration(base, polyArg, type);
    }

    @Override
    public Ast visitVarDecl(VarDeclContext ctx) {
        return null; //todo
    }

    //endregion


    /* ******************* *
     *    EXPRESSIONS      *
     * ******************* */
    // region expression


    @Override
    public Ast visitVarRefExpr(VarRefExprContext ctx) {
        return new AstVariableReference(ctx.ID().getText());
    }

    @Override
    public Ast visitPostUnopExpr(PostUnopExprContext ctx) {
        return new AstUnop(
                (AstExpression) visit(ctx.expr()),
                Operator.from(ctx.unop().getText()),
                UnopType.POST
            );
    }

    @Override
    public Ast visitPreUnopExpr(PreUnopExprContext ctx) {
        return new AstUnop(
                (AstExpression) visit(ctx.expr()),
                Operator.from(ctx.unop().getText()),
                UnopType.PRE
        );
    }

    @Override
    public AstBinop visitBinopPlusMinusExpr(BinopPlusMinusExprContext ctx) {
        AstExpression left = (AstExpression) visit(ctx.expr(0));
        Operator op = Operator.from(ctx.op.getText());
        AstExpression right = (AstExpression) visit(ctx.expr(1));
        return new AstBinop(left, right, op);
    }

    @Override
    public AstBinop visitBinopTimesDivExpr(BinopTimesDivExprContext ctx) {
        ExprContext leftCtx = ctx.expr(0);
        AstExpression left = (AstExpression) visit(leftCtx);
        Operator op = Operator.from(ctx.op.getText());
        AstExpression right = (AstExpression) visit(ctx.expr(1));
        return new AstBinop(left, right, op);
    }

    @Override
    public AstExpression visitLitteral(LitteralContext ctx) {
        if (ctx.INT() != null) {
            return new AstNumLitteral(ctx.INT().getText(), AstNumLitteral.NumberType.INTEGER);
        }
        if (ctx.DOUBLE() != null) {
            return new AstNumLitteral(ctx.DOUBLE().getText(), NumberType.FLOAT);
        }
        if (ctx.STRING_LITERAL() != null) {
            return new AstStringLitteral(ctx.STRING_LITERAL().getText());
        }
        return null; // fixme what do to here?

    }

    @Override
    public AstExpression visitParenExpr(ParenExprContext ctx) {
        return (AstExpression) visit(ctx.expr());
    }

    @Override
    public AstFuncCall visitFuncCallExpr(FuncCallExprContext ctx) {
        String name = ctx.ID().getText();
        List<AstExpression> args = ctx.expr().stream()
                .map(ct -> (AstExpression) visit(ct)).toList();
        return new AstFuncCall(name, args);
    }

    //endregion
}
