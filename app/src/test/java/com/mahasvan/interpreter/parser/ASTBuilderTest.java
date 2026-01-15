package com.mahasvan.interpreter.parser;

import com.mahasvan.interpreter.lexer.Lexer;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.operators.Add;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ASTBuilderTest {
    private ASTBuilder builder;

    private Lexer lexer;

    @BeforeEach
    public void setUp() {
        builder = new ASTBuilder();
        lexer = new Lexer();
    }

    @ParameterizedTest
    @ValueSource(strings = {"( + 1 2 )", "( + 1 ( + 2 3 ) )"})
    public void testBuildTreeWithValidSyntax(String input) {
        List<Node> tokens = lexer.tokenize(input);
        builder.build(tokens);
        Node tree = builder.getTree();
        assertEquals(Add.class, tree.getClass());
        assert tree.validate();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "( + + 1 )", "1 )"})
    public void testBuildTreeWithValidStructureButInvalidSyntaxThrowsException(String input) {
        List<Node> tokens = lexer.tokenize(input);
        assertThrows(ArithmeticException.class, () -> builder.build(tokens));
    }
}