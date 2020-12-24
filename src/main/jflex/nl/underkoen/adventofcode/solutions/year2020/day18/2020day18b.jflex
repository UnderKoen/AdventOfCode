package nl.underkoen.adventofcode.solutions.year2020.day18;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import java.io.StringReader;
import static nl.underkoen.adventofcode.solutions.year2020.day18.ParserBSym.*;

%%

%class LexerB
%public
%unicode
%cup
%line
%column
%char

%{
    private ComplexSymbolFactory sf;
    private int csline, cscolumn;

    public LexerB(String line, ComplexSymbolFactory sf) {
        this(new StringReader(line));
        this.sf = sf;
    }

    protected void emit_warning(String message) {
        System.out.println("scanner warning: " + message + " at : 2 " +
                (yyline + 1) + " " + (yycolumn + 1) + " " + yychar);
    }

    protected void emit_error(String message) {
        System.out.println("scanner error: " + message + " at : 2" +
                (yyline + 1) + " " + (yycolumn + 1) + " " + yychar);
    }
%}

Number = [0-9]+
Space = [ \t\f]
NewLine = \r | \n | \r\n

%eofval{
    return sf.newSymbol("EOF",EOF);
%eofval}

%%

<YYINITIAL> {
    {Space}             { }
    {NewLine}           { return sf.newSymbol("NEW_LINE", NEW_LINE); }

    /* expressions */
    "+"                 { return sf.newSymbol("PLUS", PLUS); }
    "-"                 { return sf.newSymbol("MINUS", MINUS); }
    "*"                 { return sf.newSymbol("TIMES", TIMES); }
    "/"                 { return sf.newSymbol("DIVIDE", DIVIDE); }
    "%"                 { return sf.newSymbol("MODULO", MODULO); }
    "="                 { return sf.newSymbol("ASSIGN", ASSIGN); }
    "("                 { return sf.newSymbol("LEFT_PAREN", LEFT_PAREN); }
    ")"                 { return sf.newSymbol("RIGHT_PAREN", RIGHT_PAREN); }
    {Number}            { return sf.newSymbol("NUMBER", NUMBER, new Long(yytext())); }

    /* statement */
    "{"                 { return sf.newSymbol("{", LEFT_CURLY); }
    "}"                 { return sf.newSymbol("}", RIGHT_CURLY); }

    /* compare */
    "=="                { return sf.newSymbol("EQUAL", EQUAL); }
    "!="                { return sf.newSymbol("NOT_EQUAL", NOT_EQUAL); }
    "<"                 { return sf.newSymbol("BIGGER", BIGGER); }
    ">"                 { return sf.newSymbol("SMALLER", SMALLER); }
    "<="                { return sf.newSymbol("BIGGER_OR_EQUALS", BIGGER_OR_EQUALS); }
    ">="                { return sf.newSymbol("SMALLER_OR_EQUALS", SMALLER_OR_EQUALS); }
}

// error fallback
[^]|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }