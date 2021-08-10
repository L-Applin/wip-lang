package ca.applin.selmer.lang.ast;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Operator {

    PLUS_EQ         ("+="),
    MINUS_EQ        ("-="),
    PLUS_PLUS       ("++"),
    MINUS_MINUS     ("--"),
    LOGICAL_OR      ("||"),
    LOGICAL_AND     ("&&"),
    DOUBLE_EQ       ("=="),
    GT              (">"),
    LT              ("<"),
    GT_EQ           (">="),
    LT_E            ("<="),
    EQ              ("="),
    PLUS            ("+"),
    MINUS           ("-"),
    TIMES           ("*"),
    DIV             ("/"),
    BIT_OR          ("|"),
    BIT_AND         ("&"),
    MOD             ("%"),
    DOT             (".")
    ;

    static Map<String, Operator> opMap = Stream.of(Operator.values()).collect(Collectors.toMap(
            op -> op.str, Function.identity()
    ));

    public static Operator from(String value) {
        return opMap.get(value);
    }

    public String str;

    Operator(String str) {
        this.str = str;
    }
}
