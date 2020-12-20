package nl.underkoen.adventofcode.solutions.year2020.day18;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import java.io.StringReader;
import nl.underkoen.adventofcode.solutions.year2020.day18.ParserBSym;

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
    return sf.newSymbol("EOF",ParserBSym.EOF);
%eofval}

%%

<YYINITIAL> {
    {Space}             { }
    {NewLine}           { return sf.newSymbol("NEW_LINE", ParserBSym.NEW_LINE); }

    /* expressions */
    "+"                 { return sf.newSymbol("PLUS", ParserBSym.PLUS); }
    "-"                 { return sf.newSymbol("MINUS", ParserBSym.MINUS); }
    "*"                 { return sf.newSymbol("TIMES", ParserBSym.TIMES); }
    "/"                 { return sf.newSymbol("DIVIDE", ParserBSym.DIVIDE); }
    "%"                 { return sf.newSymbol("MODULO", ParserBSym.MODULO); }
    "="                 { return sf.newSymbol("ASSIGN", ParserBSym.ASSIGN); }
    "("                 { return sf.newSymbol("LEFT_PAREN", ParserBSym.LEFT_PAREN); }
    ")"                 { return sf.newSymbol("RIGHT_PAREN", ParserBSym.RIGHT_PAREN); }
    {Number}            { return sf.newSymbol("NUMBER", ParserBSym.NUMBER, new Long(yytext())); }

    /* statement */
    "{"                 { return sf.newSymbol("{", ParserBSym.LEFT_CURLY); }
    "}"                 { return sf.newSymbol("}", ParserBSym.RIGHT_CURLY); }

    /* compare */
    "=="                { return sf.newSymbol("EQUAL", ParserBSym.EQUAL); }
    "!="                { return sf.newSymbol("NOT_EQUAL", ParserBSym.NOT_EQUAL); }
    "<"                 { return sf.newSymbol("BIGGER", ParserBSym.BIGGER); }
    ">"                 { return sf.newSymbol("SMALLER", ParserBSym.SMALLER); }
    "<="                { return sf.newSymbol("BIGGER_OR_EQUALS", ParserBSym.BIGGER_OR_EQUALS); }
    ">="                { return sf.newSymbol("SMALLER_OR_EQUALS", ParserBSym.SMALLER_OR_EQUALS); }
}

// error fallback
[^]|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }