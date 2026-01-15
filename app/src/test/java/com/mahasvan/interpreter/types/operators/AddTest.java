package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.lexer.Lexer;
import com.mahasvan.interpreter.parser.ASTBuilder;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AddTest {
    private EvaluationVisitor evaluationVisitor;
    private Lexer lexer;
    private ASTBuilder astBuilder;


    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
        this.lexer = new Lexer();
        this.astBuilder = new ASTBuilder();
    }

    @ParameterizedTest
    @ValueSource(strings = {"( + 1 2 )", "( + 1 ( + 2 3 ) )"})
    void testValidAddition(String input) {
        List<Node> tokens = lexer.tokenize(input);
        assert !tokens.isEmpty();
        astBuilder.build(tokens);
        Node add = astBuilder.getTree();
        assert add instanceof Add;
        assert add.validate();
        assert add.accept(evaluationVisitor) != null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"( + )", "( + 1 )"})
    void testInvalidAdditionFromStringInput(String input) {
        List<Node> tokens = lexer.tokenize(input);
        assert !tokens.isEmpty();
        // validation is called when the tree is being built
        assertThrows(
                ArithmeticException.class,
                () -> astBuilder.build(tokens));

    }

    @Test
    void testInvalidAdditionWithValidStructureButNullValues() {
        Add add = new Add();
        List<Node> operands = new ArrayList<>();
        operands.add(null);
        operands.add(null);
        add.getOperands().addAll(operands);
        assert !add.validate();
    }

}