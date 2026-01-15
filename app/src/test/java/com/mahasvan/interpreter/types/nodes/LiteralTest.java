package com.mahasvan.interpreter.types.nodes;

import com.mahasvan.interpreter.lexer.SymbolTable;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LiteralTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testLiteralEvaluation() {
        Literal literal = new Literal(123);
        assertTrue(literal.validate());
        assertEquals(123, literal.accept(evaluationVisitor));
    }
}