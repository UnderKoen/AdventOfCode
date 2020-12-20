package nl.underkoen.adventofcode.solutions.year2020.day18;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import java.io.StringReader;
import nl.underkoen.adventofcode.solutions.year2020.day18.ParserASym;

%%

%class LexerA
%public
%unicode
%cup
%line
%column
%char

%{
    private ComplexSymbolFactory sf;
    private int csline, cscolumn;

    public LexerA(String line, ComplexSymbolFactory sf) {
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
    return sf.newSymbol("EOF",ParserASym.EOF);
%eofval}

%%

<YYINITIAL> {
    {Space}             { }
    {NewLine}           { return sf.newSymbol("NEW_LINE", ParserASym.NEW_LINE); }

    /* expressions */
    "+"                 { return sf.newSymbol("PLUS", ParserASym.PLUS); }
    "-"                 { return sf.newSymbol("MINUS", ParserASym.MINUS); }
    "*"                 { return sf.newSymbol("TIMES", ParserASym.TIMES); }
    "/"                 { return sf.newSymbol("DIVIDE", ParserASym.DIVIDE); }
    "%"                 { return sf.newSymbol("MODULO", ParserASym.MODULO); }
    "="                 { return sf.newSymbol("ASSIGN", ParserASym.ASSIGN); }
    "("                 { return sf.newSymbol("LEFT_PAREN", ParserASym.LEFT_PAREN); }
    ")"                 { return sf.newSymbol("RIGHT_PAREN", ParserASym.RIGHT_PAREN); }
    {Number}            { return sf.newSymbol("NUMBER", ParserASym.NUMBER, new Long(yytext())); }

    /* statement */
    "{"                 { return sf.newSymbol("{", ParserASym.LEFT_CURLY); }
    "}"                 { return sf.newSymbol("}", ParserASym.RIGHT_CURLY); }

    /* compare */
    "=="                { return sf.newSymbol("EQUAL", ParserASym.EQUAL); }
    "!="                { return sf.newSymbol("NOT_EQUAL", ParserASym.NOT_EQUAL); }
    "<"                 { return sf.newSymbol("BIGGER", ParserASym.BIGGER); }
    ">"                 { return sf.newSymbol("SMALLER", ParserASym.SMALLER); }
    "<="                { return sf.newSymbol("BIGGER_OR_EQUALS", ParserASym.BIGGER_OR_EQUALS); }
    ">="                { return sf.newSymbol("SMALLER_OR_EQUALS", ParserASym.SMALLER_OR_EQUALS); }
}

// error fallback
[^]|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }