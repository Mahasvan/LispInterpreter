package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SubtractTest {

    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidStructure() {
        Subtract subtract = new Subtract();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(10));
        operands.add(new Literal(5));
        subtract.getOperands().addAll(operands);
        assert subtract.validate();
        assert subtract.accept(evaluationVisitor) == 5;
        subtract.getOperands().add(new Literal(5));
        assert subtract.validate();
        assert subtract.accept(evaluationVisitor) == 0;
        subtract.getOperands().add(new Literal(10));
        assert subtract.validate();
        assert subtract.accept(evaluationVisitor) == -10;
    }

    @Test
    void testInvalidStructureWithLessThanTwoOperands() {
        Subtract subtract = new Subtract();
        assert !subtract.validate();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(10));
        subtract.getOperands().addAll(operands);
    }

    @Test
    void testInvalidStructureWithNullOperand() {
        Subtract subtract = new Subtract();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(10));
        operands.add(null);
        subtract.getOperands().addAll(operands);
        assert !subtract.validate();
    }
}