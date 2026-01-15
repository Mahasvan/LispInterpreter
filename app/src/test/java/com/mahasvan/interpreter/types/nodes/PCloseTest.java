package com.mahasvan.interpreter.types.nodes;

import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PCloseTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testPCloseEvaluation() {
        PClose pClose = new PClose();
        assertTrue(pClose.validate());
        // evaluation returns null
        assertNull(pClose.accept(evaluationVisitor));
    }
}