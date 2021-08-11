lexer grammar Base ;

INT
  : MINUS? [0-9]+ ;

DOUBLE
  : MINUS? INT '.' INT? ;

STRING_LITERAL
  : '"' (~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\'))* '"' ;

KEYWORD_TYPE   : 'Type' ;
KEYWORD_IF     : 'if' ;
KEYWORD_ELSE   : 'else' ;
KEYWORD_WHILE  : 'while' ;
KEYWORD_RETURN : 'return' ;
KEYWORD_FOR    : 'for' ;
UNIT           : 'Unit' ;
VOID           : 'Void' ;

ID : IdentifierChars ;

IdentifierChars
  : [a-zA-Z_] [a-zA-Z_0-9]*;


ARROW         : '->' ;
PLUS_EQ       : '+=' ;
MINUS_EQ      : '-=' ;
DOUBLE_COLON  : '::' ;
PLUS_PLUS     : '++' ;
MINUS_MINUS   : '--' ;
LOGICAL_OR    : '||' ;
LOGICAL_AND   : '&&' ;
COLON_EQ      : ':=' ;
GT_EQ         : '>=' ;
LT_EQ         : '<=' ;
DOUBLE_EQ     : '==' ;
NEQ           : '!=' ;
EQ            : '=' ;
PLUS          : '+' ;
MINUS         : '-' ;
TIMES         : '*' ;
DIV           : '/' ;
BIT_OR        : '|' ;
BIT_AND       : '&' ;
COLON         : ':' ;
GT            : '>' ;
LT            : '<' ;
MOD           : '%' ;
OPEN_PAREN    : '(' ;
CLOSE_PAREN   : ')' ;
OPEN_SQUARE   : '[' ;
CLOSE_SQUARE  : ']' ;
OPEN_CURLY    : '{' ;
CLOSE_CURLY   : '}' ;
COMMA         : ',' ;
DOT           : '.' ;

//UNIT : '()' ;

WS       : (' ' | '\t') -> skip ;
NEWLINE  : ('\r'? '\n' | '\n')+ -> skip ;
SEP      : ';' -> skip ;
COMMENT  : '//' (.)*? NEWLINE -> skip;
