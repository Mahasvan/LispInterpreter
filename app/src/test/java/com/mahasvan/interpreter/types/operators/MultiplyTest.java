package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MultiplyTest {

    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidStructure() {
        Multiply multiply = new Multiply();
        multiply.getOperands().add(new Literal(2));
        multiply.getOperands().add(new Literal(3));
        assert multiply.accept(evaluationVisitor) == 6;
        multiply.getOperands().add(new Literal(-1));
        assert multiply.accept(evaluationVisitor) == -6;
        multiply.getOperands().add(new Literal(1));
        assert multiply.accept(evaluationVisitor) == -6;
    }

    @Test
    void testInvalidStructureWithLessThanTwoOperands() {
        Multiply multiply = new Multiply();
        assert !multiply.validate();
        multiply.getOperands().add(new Literal(2));
        assert !multiply.validate();
        multiply.getOperands().add(new Literal(3));
        assert multiply.validate();
        multiply.getOperands().add(new Literal(3));
        assert multiply.validate();
    }

    @Test
    void testInvalidStructureWithNullValues() {
        Multiply multiply = new Multiply();
        multiply.getOperands().add(new Literal(2));
        multiply.getOperands().add(null);
        assert !multiply.validate();
    }

}