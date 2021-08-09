// todo
//   [decl]           struct member assignement : 'myType.elem = 12'
//   [decl]           Pattern matching for sum type, function declaration
//   [stmt]           for statement : 'for range(0, 10) { x: Int = it }'
//   [expr]           List access : list[0]
//   [expr]           struct instanciation
//   [expr]           struct member access 'myVar : Int = myStruct.elem'
//   [expr]           array/list litteral
//   [expr]           function call 'ID' should be replaced by an expression that must type resolve to a function trype
//   [expr]           lambda expression :
//   [lang]           type classes and implementation
//   [lang]           heap vs stack ???
//   [bug:type/fun]   Unit type '()' breaks empty function call 'myFunc()'

// done
//   [decl]                    Variable assignement : 'myVar = 12'
//   [expr/decl]               function declaration
//   [bug:2021-08-08]          code block should be properly seperated by line break or ';'
//   [bug:type]                in sum types, a constructor with multiple poly args is parsed as a single poly arg instead of multiple constructor argument

grammar Lang ;
import Base ;
@header {
    import ca.applin.selmer.lang.ast.*;
    import ca.applin.selmer.lang.ast.type.*;
    import java.util.*;
}


// *****************************
//  LANG
// *****************************
lang returns [ Ast ast ]
  : (d+=decl)+ EOF { $ast = new AstCodeBlock($d.stream().map(tree-> tree.ast).toList()); }
  ;

//code returns [ Ast ast ]
//  : decl { $ast = $decl.ast; }
//  | expr { $ast = $expr.ast; }
//  | stmt { $ast = $stmt.ast; }
//  ;









// *****************************
//  TYPES
// *****************************
type returns [ AstType ast ]
  : '(' t=type ')' { $ast = $t.ast; }
  | <assoc=right> typeList ARROW r=type
    { $ast = new AstTypeFunction($typeList.types, $r.ast); }
  | <assoc=right> a=type ARROW r=type
    { List<AstType> args = new ArrayList();
      args.add($a.ast);
      $ast = new AstTypeFunction(args, $r.ast); }
  | simpleType
    { $ast = new AstTypeSimple($simpleType.s); }
  | genericType
    { $ast = $genericType.ast; }
  | arrayType
    { $ast = $arrayType.ast; }
  | tupleType
    { $ast = new AstTypeTuple($tupleType.types); }
  | <assoc=right> sumType
    { $ast = $sumType.ast; }
  | UNIT
    { $ast = AstType.UNIT; }
  | VOID
    { $ast = AstType.VOID; }
  ;


typeList returns [ List<AstType> types ]
  : '(' type (',' type)* ')'
    { $types = $ctx.type().stream().map(t -> t.ast).toList(); }
  ;

sumTypeElem returns [ AstSumType.SumTypeConstructor elem ]
  : id=ID t+=type* { $elem = new AstSumType.SumTypeConstructor($id.text, $t.stream().map(tp -> tp.ast).toList()); }
  ;

sumType returns [ AstSumType ast ]
  : ste+=sumTypeElem ('|' ste+=sumTypeElem)+
    { $ast = new AstSumType($ste.stream().map(elem -> elem.elem).toList()); }
  ;

genericType returns [ AstTypePoly ast ]
  : simpleType (ID)+
    { $ast = new AstTypePoly($simpleType.text, $ctx.ID().stream().map(ParseTree::getText).toList()); }
  ;

simpleType returns [ String s ]
  : ID { $s = $ID.text; }
  ;

tupleType returns [ List<AstType> types ]
  : t=typeList { $types = $t.types; } ;

arrayType returns [ AstType ast ]
  : '[' type ']'
    { $ast = new AstTypeArray($type.ast); }
  ;









// *****************************
//  DECLARATIONS
// *****************************
decl returns [ Ast ast ]
  : typeDecl        { $ast = $typeDecl.ast; }
  | structDecl      { $ast = $structDecl.ast; }
  | funcDecl        { $ast = $funcDecl.ast; }
  | inferedVarDecl  { $ast = $inferedVarDecl.ast; }
  | varDecl         { $ast = $varDecl.ast; }
  ;

typeDecl returns [ AstTypeDeclaration ast ]
  : gen=genericType DOUBLE_COLON KEYWORD_TYPE EQ t=type
    { $ast = new AstTypeDeclaration($gen.ast.name, $gen.ast.polyArg, $t.ast); }
  | sim=simpleType DOUBLE_COLON KEYWORD_TYPE EQ t=type
    { $ast = new AstTypeDeclaration($sim.s, new ArrayList(), $t.ast); }
  ;

structDecl returns [ AstStructDeclaration ast ]
  : gen=genericType DOUBLE_COLON KEYWORD_TYPE '{' sml=structMemberList '}'
    { $ast = new AstStructDeclaration($gen.ast.name, $gen.ast.polyArg, $sml.list); }
  | sim=simpleType DOUBLE_COLON KEYWORD_TYPE '{' sml=structMemberList '}'
    { $ast = new AstStructDeclaration($sim.s, new ArrayList(), $sml.list); }
  ;

funcArg returns [ AstFunctionDeclaration.AstFunctionArgs ast ]
  : name=ID (COLON t=type)?
    { $ast = new AstFunctionDeclaration.AstFunctionArgs($name.text, _localctx.t == null ? AstType.UNKNOWN : $t.ast); }
  ;

funcArgList returns [ List<AstFunctionDeclaration.AstFunctionArgs> list ]
  : fa+=funcArg (',' fa+=funcArg)*
    { $list = $fa.stream().map(c -> c.ast).toList(); }
  ;

funcBody returns [ AstFunctionDeclaration.AstFunctionBody ast ]
  // empty body
  : '{' '}'
    { $ast = new AstFunctionDeclaration.AstFunctionBody(new ArrayList()); }
  // single expr no return
  | expr
    { $ast =  new AstFunctionDeclaration.AstFunctionBody(new ArrayList(){{ add($expr.ast); }}); }
  // full code block, return
  | '{' c+=codeBlockContent (c+=codeBlockContent)* '}'
    { $ast = new AstFunctionDeclaration.AstFunctionBody($c.stream().map(code -> code.ast).toList()); }
  ;

funcDecl returns [ AstFunctionDeclaration ast ]
  // single argument no parentheses around
  : name=ID DOUBLE_COLON (t=type EQ)? arg=funcArg ARROW body=funcBody
    { $ast = new AstFunctionDeclaration(
             $name.text,
             _localctx.t == null ? AstType.UNKNOWN : $t.ast,
             new ArrayList() {{ add($arg.ast); }},
             $body.ast);
     }
  | name=ID DOUBLE_COLON (t=type EQ)? '(' fa=funcArgList? ')' ARROW body=funcBody
    { $ast = new AstFunctionDeclaration(
              $name.text,
              _localctx.t == null ? AstType.UNKNOWN : $t.ast,
              $fa.list,
              $body.ast);
    }
 ;

structMemberList returns [ List<AstStructMemberDeclaration> list ]
  : (sm+=structMember (sm+=structMember)*)?
    { $list = $sm == null ? new ArrayList() : $sm.stream().map(ct -> ct.ast).toList(); }
  ;

structMember returns [ AstStructMemberDeclaration ast ]
  : varDecl
    { $ast = new AstStructMemberDeclaration($varDecl.ast.varName, $varDecl.ast.type, $varDecl.ast.initExpr); }
  | inferedVarDecl
    { $ast = new AstStructMemberDeclaration($inferedVarDecl.ast.varName, $inferedVarDecl.ast.type, $inferedVarDecl.ast.initExpr); }
  ;

inferedVarDecl returns [ AstVariableDeclaration ast ]
  : id=ID COLON_EQ e=expr
    { $ast = new AstVariableDeclaration($id.text, AstType.UNKNOWN, $e.ast); }
  ;

varDecl returns [ AstVariableDeclaration ast ]
  : id=ID COLON t=type (EQ ex=expr)?
    { $ast = new AstVariableDeclaration($id.text, $t.ast, $ctx.expr() == null ? null : $ex.ast); }
  ;









// *****************************
//  EXPRESSIONS
// *****************************
expr returns [ AstExpression ast ]
  : OPEN_PAREN ex=expr CLOSE_PAREN
    { $ast = $ex.ast; }
  | left=expr MOD right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.MOD); }
  | left=expr op=(DIV | TIMES) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); }
  | left=expr op=(MINUS | PLUS) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); }
  | left=expr op=(BIT_OR | BIT_AND) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); }
  | left=expr op=(LOGICAL_OR | LOGICAL_AND) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); }
  | left=expr op=(GT | LT | GT_EQ | LT_EQ) right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.from($op.text)); }
  | left=expr DOUBLE_EQ right=expr
    { $ast = new AstBinop($left.ast, $right.ast, Operator.DOUBLE_EQ); }
  | unop ex=expr
    { $ast = new AstUnop($ex.ast, Operator.from($unop.text), AstUnop.UnopType.PRE); }
  | ex=expr unop
    { $ast = new AstUnop($ex.ast, Operator.from($unop.text), AstUnop.UnopType.POST); }
  | funcCall
    { $ast = $funcCall.ast; }
  | id=ID
    { $ast = new AstVariableReference($id.text); }
  | litteral
    { $ast = $litteral.ast; }
  ;

funcCall returns [ AstFuncCall ast ]
  : name=ID OPEN_PAREN CLOSE_PAREN  // empty function call
    { $ast = new AstFuncCall($name.text, new ArrayList()); }
    // todo: name=ID should be replaced by an expression that could return a function type
  | name=ID OPEN_PAREN e+=expr (COMMA e+=expr)*  CLOSE_PAREN // function call
    { $ast = new AstFuncCall($name.text, $e.stream().map(expr -> expr.ast).toList()); }
    // todo: name=ID should be replaced by an expression that could return a function type ;
  ;

unop
  : PLUS_PLUS | MINUS_MINUS
  ;

litteral returns [ AstExpression ast ]
  : i=INT            { $ast = new AstNumLitteral($i.text, AstNumLitteral.NumberType.INTEGER); }
  | d=DOUBLE         { $ast = new AstNumLitteral($d.text, AstNumLitteral.NumberType.FLOAT); }
  | s=STRING_LITERAL { $ast = new AstStringLitteral($s.text); }
  ;









// *****************************
//  STATEMENTS
// *****************************
stmt returns [ AstStatement ast ]
  : codeBlock           { $ast = $codeBlock.ast; }
  | ifStatement         { $ast = $ifStatement.ast; }
  | whileStatement      { $ast = $whileStatement.ast; }
  | variableAssignement { $ast = $variableAssignement.ast; }
  ;

codeBlockContent returns [ Ast ast ]
  : s=stmt
    { $ast = $s.ast; }
  | d=decl
    { $ast = $d.ast; }
  | e=expr
    { $ast = $e.ast; }
  | KEYWORD_RETURN e=expr
    { $ast = new AstReturnExpression($e.ast); }
  ;

codeBlock returns [ AstCodeBlock ast ]
  : '{' '}'
    { $ast = new AstCodeBlock(new ArrayList()); }
  | '{' c+=codeBlockContent (c+=codeBlockContent)* '}'
    { $ast = new AstCodeBlock($c.stream().map(q -> q.ast).toList()); }
  ;

ifStatement returns [ AstIfStatement ast ]
  // single line if
  : KEYWORD_IF e=expr ifBlock=codeBlockContent
    { List<Ast> code = new ArrayList();
      code.add($ifBlock.ast);
      $ast = new AstIfStatement($e.ast, new AstCodeBlock(code), AstCodeBlock.empty());
    } #singleIf
  | KEYWORD_IF e=expr ifBlock=codeBlock (KEYWORD_ELSE elseBlock=codeBlock)?
    { $ast = new AstIfStatement($e.ast, $ifBlock.ast, $elseBlock.ast); } #multipleIf
  ;

whileStatement returns [ AstWhileStatement ast ]
  : KEYWORD_WHILE e=expr codeBlock
    { $ast = new AstWhileStatement($e.ast, $codeBlock.ast); }
  ;

variableAssignement returns [ AstVariableAssignement ast ]
  : id=ID EQ e=expr
    { $ast = new AstVariableAssignement($id.text, $e.ast); }
  ;

