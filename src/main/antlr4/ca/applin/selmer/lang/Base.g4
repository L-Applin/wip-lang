lexer grammar Base ;

INT
    : MINUS? [0-9]+ ;

DOUBLE
    : MINUS? INT '.' INT? ;

STRING_LITERAL
    : '"' (~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\'))* '"' ;

KEYWORD_TYPE : 'Type' ;

ID : IdentifierChars ;

IdentifierChars
    : [a-zA-Z_]+;


PLUS_EQ       : '+=' ;
MINUS_EQ      : '-=' ;
DOUBLE_COLON  : '::' ;
PLUS_PLUS     : '++' ;
MINUS_MINUS   : '--' ;
LOGICAL_OR    : '||' ;
LOGICAL_AND   : '&&' ;
COLON_EQ      : ':=' ;
EQ            : '=' ;
PLUS          : '+' ;
MINUS         : '-' ;
TIMES         : '*' ;
DIV           : '/' ;
BIT_OR        : '|' ;
BIT_AND       : '&' ;
COLON         : ':' ;

WS                  : (' ' | '\t') -> skip ;
NEWLINE             : ('\r'? '\n' | '\r')+ -> skip ;
ARROW               : '->' ;