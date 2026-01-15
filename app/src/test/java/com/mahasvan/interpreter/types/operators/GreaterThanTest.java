package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GreaterThanTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidStructureWithTrueCondition() {
        GreaterThan greaterThan = new GreaterThan();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(2));
        operands.add(new Literal(1));
        greaterThan.getOperands().addAll(operands);
        assert greaterThan.validate();
        assert greaterThan.accept(evaluationVisitor) == 1;
    }

    @Test
    void testValidStructureWithFalseCondition() {
        GreaterThan greaterThan = new GreaterThan();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(2));
        operands.add(new Literal(3));
        greaterThan.getOperands().addAll(operands);
        assert greaterThan.validate();
        assert greaterThan.accept(evaluationVisitor) == 0;
    }

    @Test
    void testInvalidStructureWithLessThanTwoOperands() {
        GreaterThan greaterThan = new GreaterThan();
        List<Node> operands = new ArrayList<>();
        assert !greaterThan.validate();
        operands.add(new Literal(2));
        greaterThan.getOperands().addAll(operands);
        assert !greaterThan.validate();
    }

    @Test
    void testInvalidStructureWithNullOperand() {
        GreaterThan greaterThan = new GreaterThan();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(2));
        operands.add(null);
        greaterThan.getOperands().addAll(operands);
        assert !greaterThan.validate();
    }
}