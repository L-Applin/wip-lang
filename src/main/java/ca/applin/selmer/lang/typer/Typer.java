package ca.applin.selmer.lang.typer;

import ca.applin.selmer.lang.AstBaseVisitor;
import ca.applin.selmer.lang.ast.AstBinop;
import ca.applin.selmer.lang.ast.AstExpression;
import ca.applin.selmer.lang.ast.AstFuncCall;
import ca.applin.selmer.lang.ast.AstFunctionDeclaration;
import ca.applin.selmer.lang.ast.AstNumLitteral;
import ca.applin.selmer.lang.ast.AstStringLitteral;
import ca.applin.selmer.lang.ast.type.AstType;
import ca.applin.selmer.lang.ast.type.DoubleType;
import ca.applin.selmer.lang.ast.type.IntType;
import java.util.List;

public class Typer extends AstBaseVisitor<AstType> {


    @Override
    public AstType visit(AstStringLitteral astStringLitteral) {
        return astStringLitteral.type;
    }

    @Override
    public AstType visit(AstNumLitteral astNumLitteral) {
        return astNumLitteral.type;
    }

    @Override
    public AstType visit(AstBinop binop) {
        if (binop.type.isKnown()) {
            return binop.type;
        }
        AstType left = binop.left.visit(this);
        AstType right = binop.right.visit(this);
        if (left.equals(right)) {
            binop.type = left;
            return binop.type;
        }
        if (intAndDoubleMismatch(left, right)) {
            binop.type = DoubleType.INSTANCE;
            return binop.type;
        }
        throw new MismatchTypeException("Binop '%s' operands types left:%s and right:%s are different"
            .formatted(binop.operator, left.toString(), right.toString()));
    }

    private boolean intAndDoubleMismatch(AstType left, AstType right) {
        return (left.equals(DoubleType.INSTANCE) && right.equals(IntType.INSTANCE))
            || (left.equals(IntType.INSTANCE) && right.equals(DoubleType.INSTANCE));
    }

    @Override
    public AstType visit(AstFunctionDeclaration astFunctionDeclaration) {
        // infer arg types
        List<AstType> argTypes;

        // infer return type
        // single expression only
        AstType inferedReturnType = astFunctionDeclaration.body.code().size() == 1
                ? inferSingleExpressionFunctionReturnType(astFunctionDeclaration)
                : inferFunctionReturnType(astFunctionDeclaration);
        if (astFunctionDeclaration.type.isKnown()) {

        }
        return null;
    }

    private AstType inferFunctionReturnType(AstFunctionDeclaration astFunctionDeclaration) {
        // find all return statement, make sure they are of the same type, return that type
        return null;
    }


    private AstType inferSingleExpressionFunctionReturnType(AstFunctionDeclaration astFunc) {
        assert astFunc.body.code().size() == 1
                : "Function %s must have only a single expression to infer type, but was found to have %d"
                    .formatted(astFunc.name, astFunc.body.code().size());
        AstExpression expr = (AstExpression) astFunc.body.code().get(0);
        AstType inferedReturnType = expr.visit(this);
        return null;
    }

    @Override
    public AstType visit(AstFuncCall astExpression) {
        // todo
        return astExpression.type;
    }
}
