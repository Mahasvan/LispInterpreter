package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LessThanTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidStructureWithTrueCondition() {
        LessThan lessThan = new LessThan();
        List<Node> operands = new ArrayList<>();
        Node op1 = new Literal(2);
        Node op2 = new Literal(3);
        operands.add(op1);
        operands.add(op2);
        lessThan.getOperands().addAll(operands);
        assert lessThan.validate();
        assert lessThan.accept(evaluationVisitor) == 1;
        operands.add(new Literal(4));
        assert lessThan.validate();
        assert lessThan.accept(evaluationVisitor) == 1;
    }

    @Test
    void testValidStructureWithFalseCondition() {
        LessThan lessThan = new LessThan();
        List<Node> operands = new ArrayList<>();
        Node op1 = new Literal(4);
        Node op2 = new Literal(3);
        operands.add(op1);
        operands.add(op2);
        lessThan.getOperands().addAll(operands);
        assert lessThan.validate();
        // 4 < 3 = false
        assert lessThan.accept(evaluationVisitor) == 0;
        operands.add(new Literal(4));
        assert lessThan.validate();
        // 4 < 3 < 4 = false
        assert lessThan.accept(evaluationVisitor) == 0;
    }

    @Test
    void testInvalidStructureWithLessThanTwoOperands() {
        LessThan lessThan = new LessThan();
        assert !lessThan.validate();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(2));
        lessThan.getOperands().addAll(operands);
        assert !lessThan.validate();
    }

    @Test
    void testInvalidStructureWithNullOperand() {
        LessThan lessThan = new LessThan();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(2));
        operands.add(null);
        lessThan.getOperands().addAll(operands);
        assert !lessThan.validate();
    }
}