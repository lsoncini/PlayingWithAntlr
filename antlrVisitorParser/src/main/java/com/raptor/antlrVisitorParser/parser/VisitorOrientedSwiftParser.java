
package com.raptor.antlrVisitorParser.parser;


import com.raptor.antlrVisitorParser.antlr4.grammars.swift.Swift3BaseVisitor;
import com.raptor.antlrVisitorParser.antlr4.grammars.swift.Swift3Lexer;
import com.raptor.antlrVisitorParser.antlr4.grammars.swift.Swift3Parser;
import com.raptor.antlrVisitorParser.antlr4.grammars.swift.Swift3Visitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class VisitorOrientedSwiftParser implements AbstractParser<String> {

    public String parse(Reader source) throws IOException{
        CharStream charStream = CharStreams.fromReader(source);
        Swift3Lexer lexer = new Swift3Lexer(null);
        lexer.reset();
        lexer.setInputStream(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        Swift3Parser parser = new Swift3Parser(tokens);
        Swift3Visitor aSwiftVisitor = new UselessOverridingMethodVisitor();
        ParseTree tree = null;
        try {
            Method rootMethod = parser.getClass().getMethod(parser.getRuleNames()[0]);
            tree = (ParseTree) rootMethod.invoke(parser);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        aSwiftVisitor.visit(tree);
        return "success";
    }

    private static class UselessOverridingMethodVisitor extends Swift3BaseVisitor<String> {
        @Override
        public String visitFunction_declaration(Swift3Parser.Function_declarationContext ctx) {
            System.out.println("ENTERING FUNCTION " + ctx.getText());
//            System.out.println("FUNC BODY: " + ctx.function_body().getText());
//            System.out.println("FUNC SIGNATURE : " + ctx.function_signature().getText());
//            System.out.println("FUNC HEAD : " + ctx.function_head().getText());
//            System.out.println("FUNC NAME : " + ctx.function_name().getText());
            Boolean isOverride = new IsOverrideMethodVisitor().visitFunction_head(ctx.function_head());
            if(isOverride){
                System.out.println("CHECKING OVERRIDE FUNCTION");

            } else {
                System.out.println("NOT AN OVERRIDE FUNCTION");
            }
            System.out.println();
            return "WORK DONE!";
        }
    }
    private static class IsOverrideMethodVisitor extends Swift3BaseVisitor<Boolean> {

        @Override
        public Boolean visitFunction_head(Swift3Parser.Function_headContext ctx) {
            Swift3Parser.Declaration_modifiersContext declaration_modifiers = ctx.declaration_modifiers();
            if(declaration_modifiers == null){
                return false;
            }
            List<Swift3Parser.Declaration_modifierContext> modifiers = ctx.declaration_modifiers().declaration_modifier();
            return modifiers.stream().anyMatch(modifier -> modifier.getText().equals("override"));
        }
    }
    private static class MyDummySwiftVisitor extends Swift3BaseVisitor<String> {
        @Override
        public String visitFunction_name(Swift3Parser.Function_nameContext ctx) {
            System.out.println();
            System.out.println("ENTERING FUNCTION_NAME " + ctx.getText());
            Optional.ofNullable(ctx.declaration_identifier()).ifPresent(id -> System.out.println("CTX_DECLARATION_ID: " + id.getText()));
            Optional.ofNullable(ctx.operator()).ifPresent(op -> System.out.println("FUNC OPERATOR: " + op.getText()));
            System.out.println();
            return "WORK DONE!";
        }

        @Override
        public String visitFunction_declaration(Swift3Parser.Function_declarationContext ctx) {
            System.out.println();
            System.out.println("ENTERING FUNCTION " + ctx.getText());
            System.out.println("FUNC BODY: " + ctx.function_body().getText());
            System.out.println("FUNC SIGNATURE : " + ctx.function_signature().getText());
            System.out.println("FUNC HEAD : " + ctx.function_head().getText());
            System.out.println("FUNC NAME : " + ctx.function_name().getText());
            this.visitFunction_name(ctx.function_name());
            return "WORK DONE!";
        }
    }
}
