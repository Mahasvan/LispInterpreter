package com.mahasvan.interpreter.types.nodes;

import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VariableTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testVariableEvaluation() {
        Variable variable = new Variable();
        variable.value = new Literal(123);
        assertTrue(variable.validate());
        assertEquals(123, variable.accept(evaluationVisitor));
    }

}