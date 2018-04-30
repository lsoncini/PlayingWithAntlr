package com.raptor.antlrVisitorParser.parser;

import java.io.IOException;
import java.io.Reader;

public interface AbstractParser<T> {
    T parse(Reader source) throws IOException;
}
