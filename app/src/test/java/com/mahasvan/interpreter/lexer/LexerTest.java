package com.mahasvan.interpreter.lexer;

import com.mahasvan.interpreter.types.nodes.*;
import com.mahasvan.interpreter.types.operators.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
public class LexerTest {

    private Lexer lexer;

    @BeforeEach
    public void setUp() {
        SymbolTable.getInstance().table.clear();
        lexer = new Lexer();
    }

    @Test
    public void testEmptyStatementReturnsEmptyList() {
        List<Node> tokens = lexer.tokenize("");
        assert tokens.isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"(abc", "+12a"})
    public void testInvalidTokenReturnsNull(String statement) {
        Assertions.assertThrows(
                ArithmeticException.class,
                () -> lexer.tokenize(statement)
        );
    }

    @Test
    public void testOpenParenthesisTokenization() {
        List<Node> tokens = lexer.tokenize("(");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == POpen.class;
    }

    @Test
    public void testCloseParenthesisTokenization() {
        List<Node> tokens = lexer.tokenize(")");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == PClose.class;
    }

    @Test
    public void testAddTokenization() {
        List<Node> tokens = lexer.tokenize("+");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == Add.class;
    }

    @Test
    public void testSubtractionTokenization() {
        List<Node> tokens = lexer.tokenize("-");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == Subtract.class;
    }

    @Test
    public void testDivideTokenization() {
        List<Node> tokens = lexer.tokenize("/");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == Divide.class;
    }

    @Test
    public void testMultiplyTokenization() {
        List<Node> tokens = lexer.tokenize("*");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == Multiply.class;
    }

    @Test
    public void testDefvarTokenization() {
        List<Node> tokens = lexer.tokenize("defvar");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == DefVar.class;
    }

    @Test
    public void testGreaterThanTokenization() {
        List<Node> tokens = lexer.tokenize(">");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == GreaterThan.class;
    }

    @Test
    public void testLessThanTokenization() {
        List<Node> tokens = lexer.tokenize("<");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == LessThan.class;
    }

    @Test
    public void testEqualsTokenization() {
        List<Node> tokens = lexer.tokenize("=");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == Equals.class;
    }

    @Test
    public void testIfStatementTokenization() {
        List<Node> tokens = lexer.tokenize("if");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == IfStatement.class;
    }

    @Test
    public void testLiteralTokenization() {
        List<Node> tokens = lexer.tokenize("123");
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == Literal.class;
    }

    @Test
    public void testVariableTokenization() {
        List<Node> tokens = lexer.tokenize("defvar v");
        assert tokens.size() == 2;
        assert tokens.get(1).getClass() == Variable.class;
    }

    @Test
    public void testVariableTokenizationReturnsSameObject() {
        List<Node> tokens = lexer.tokenize("defvar v v");
        assert tokens.size() == 3;
        assert tokens.get(1) == tokens.getLast();
    }

    @Test
    public void testMultipleTokens() {
        List<Node> tokens = lexer.tokenize("defvar v + 123 if");
        assert tokens.size() == 5;
        assert tokens.get(0).getClass() == DefVar.class;
        assert tokens.get(1).getClass() == Variable.class;
        assert tokens.get(2).getClass() == Add.class;
        assert tokens.get(3).getClass() == Literal.class;
        assert tokens.get(4).getClass() == IfStatement.class;
    }

    @Test
    public void testUndefinedVariableThrowsException() {
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> lexer.tokenize("v")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 123, 999999})
    public void testLiteralHasValuePopulated(int value) {
        List<Node> tokens = lexer.tokenize(String.valueOf(value));
        assert tokens.size() == 1;
        assert tokens.getFirst().getClass() == Literal.class;
        assert ((Literal) tokens.getFirst()).value == value;
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1 2 3", " 1   2  3", "1 2    3  ", "    1 2 3   "
    })
    // three literals 1 2 and 3, with different spaces between them.
    public void testSpacesBetweenTokens(String statement) {
        List<Node> tokens = lexer.tokenize(statement);
        assert tokens.size() == 3;
        for (Node token : tokens) {
            assert token.getClass() == Literal.class;
        }
        assert ((Literal) tokens.getFirst()).value == 1;
        assert ((Literal) tokens.get(1)).value == 2;
        assert ((Literal) tokens.getLast()).value == 3;
    }
}