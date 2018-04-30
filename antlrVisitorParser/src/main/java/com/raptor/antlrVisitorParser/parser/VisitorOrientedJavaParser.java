package com.raptor.antlrVisitorParser.parser;


import com.raptor.antlrVisitorParser.antlr4.grammars.java.JavaLexer;
import com.raptor.antlrVisitorParser.antlr4.grammars.java.JavaParser;
import com.raptor.antlrVisitorParser.antlr4.grammars.java.JavaParserBaseVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

import java.io.IOException;
import java.io.Reader;

public class VisitorOrientedJavaParser implements AbstractParser<RuleNode> {

    public RuleNode parse(Reader source) throws IOException{
        CharStream charStream = CharStreams.fromReader(source);
        JavaLexer lexer = new JavaLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree pt = parser.compilationUnit();
        //MyJavaVisitor aJavaVisitor = new MyJavaVisitor();
        return null;
    }

    private static class MyJavaVisitor extends JavaParserBaseVisitor<RuleNode> {

    }
}
