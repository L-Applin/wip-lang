grammar Lang ;
import Base ;
@header {
    import ca.applin.selmer.lang.ast.*;
    import ca.applin.selmer.lang.ast.type.*;
    import java.util.*;
}

lang returns [ Ast ast ]
  : decl { $ast = $decl.ast; }
  | expr { $ast = $expr.ast; }
  ;





// TYPES
type returns [ AstType ast ]
  : typeList ARROW type
    { $ast = new AstTypeFunction($typeList.types, $type.ast); } #funcManyType
  | a=type ARROW r=type
    { List<AstType> args = new ArrayList();
      args.add($a.ast);
      $ast = new AstTypeFunction(args, $r.ast); } #funcOneType
  | arrayType
    { $ast = $arrayType.ast; } #typeArray
  | tupleType
    { $ast = new AstTypeTuple($tupleType.types); } #typeTuple
  | genericType
    { $ast = $genericType.ast; } #typeGeneric
  | simpleType
    { $ast = new AstTypeSimple($simpleType.s); } #typeSimple
  | '()'
     { $ast = AstType.UNIT; } #unit
  ;

typeList returns [ List<AstType> types ]
  : '(' type (',' type)* ')'
  { $types = $ctx.type().stream().map(t -> t.ast).toList(); }
  ;

genericType returns [ AstTypePoly ast ]
  : simpleType (ID)+
  { $ast = new AstTypePoly($simpleType.text, $ctx.ID().stream().map(ParseTree::getText).toList()); }
  ;

simpleType returns [ String s ]
  : ID
  { $s = $ID.text; }
  ;

tupleType returns [ List<AstType> types ]
  : t=typeList { $types = $t.types; } ;

arrayType returns [ AstType ast ]
  :  '[' type ']'
  { $ast = new AstTypeArray($type.ast); }
  ;





// DECLARATIONS
/*
Person A :: Type {
  name: String
  age: Int = 0
  some: Maybe A = Nothing

}

*/
decl returns [ AstDeclaration ast ]
  : typeDecl { $ast = $typeDecl.ast; }
  | structDecl
  | inferedVarDecl { $ast = $inferedVarDecl.ast; }
  | varDecl { $ast = $varDecl.ast; }
  ;

typeDecl returns [ AstTypeDeclaration ast ]
  : gen=genericType DOUBLE_COLON KEYWORD_TYPE EQ t=type ('\n' | ';' )*
    { $ast = new AstTypeDeclaration($gen.ast.name, $gen.ast.polyArg, $t.ast); }
  | sim=simpleType DOUBLE_COLON KEYWORD_TYPE EQ t=type ('\n' | ';' )*
    { $ast = new AstTypeDeclaration($sim.s, new ArrayList(), $t.ast); }
  ;

structMember
  : ids+=ID (ids+=ID)* COLON type (EQ expr)? ;

structDecl
  : gen=genericType DOUBLE_COLON KEYWORD_TYPE '{' structMember ('\n' | ';' structMember)* '}'
  | sim=simpleType DOUBLE_COLON KEYWORD_TYPE '{' structMember ('\n' | ';' structMember)* '}'
  ;

inferedVarDecl returns [ AstVariableDeclaration ast ]
  : id=ID COLON_EQ e=expr
    { $ast = new AstVariableDeclaration($id.text, AstType.UNKNOWN, $e.ast); }
  ;

varDecl returns [ AstVariableDeclaration ast ]
  : id=ID COLON t=type (EQ ex=expr)? ('\n' | ';')
    { $ast = new AstVariableDeclaration($id.text, $t.ast, $ctx.expr() == null ? null : $ex.ast); }
  ;





// EXPRESSIONS
expr returns [ AstExpression ast ]
  : '(' ex=expr ')' { $ast = $ex.ast; } #parenExpr
  | left=expr op=(DIV | TIMES) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); } #binopTimesDivExpr
  | left=expr op=(MINUS | PLUS) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); } #binopPlusMinusExpr
  | left=expr op=(LOGICAL_OR | LOGICAL_AND) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); } #binopLogExpr
  | left=expr op=(BIT_OR | BIT_AND) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); }  #binopBitExpr
  | unop ex=expr
    { $ast = new AstUnop($ex.ast, Operator.from($unop.text), AstUnop.UnopType.PRE); }
    #preUnopExpr
  | ex=expr unop
    { $ast = new AstUnop($ex.ast, Operator.from($unop.text), AstUnop.UnopType.POST); }
    #postUnopExpr
  | name=ID '(' (e+=expr (',' e+=expr)*)?  ')'
    { $ast = new AstFuncCall($name.text, $e.stream().map(expr -> expr.ast).toList()); }
    #funcCallExpr
  | id=ID
    { $ast = new AstVariableReference($id.text); }
    #varRefExpr
  | litteral { $ast = $litteral.ast; } #litteralExpr
  ;

unop
  : PLUS_PLUS | MINUS_MINUS
  ;

litteral returns [ AstExpression ast ]
  : i=INT            { $ast = new AstNumLitteral($i.text, AstNumLitteral.NumberType.INTEGER); }
  | d=DOUBLE         { $ast = new AstNumLitteral($d.text, AstNumLitteral.NumberType.FLOAT); }
  | s=STRING_LITERAL { $ast = new AstStringLitteral($s.text); }
  ;
