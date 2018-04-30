package com.raptor.antlrVisitorParser;

import com.raptor.antlrVisitorParser.parser.VisitorOrientedSwiftParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length != 2 || !args[0].equals("-f")) {
            throw new IllegalArgumentException("You must specify a filePath.");
        }
        final String filePath = args[1];
        System.out.println(filePath);
        try {
            final String result = new VisitorOrientedSwiftParser().parse(new FileReader(new File(filePath)));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
