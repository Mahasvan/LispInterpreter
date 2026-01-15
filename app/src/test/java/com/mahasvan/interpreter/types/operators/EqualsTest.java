package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EqualsTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidEqualsStructure() {
        Equals equals = new Equals();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(3));
        Add add = new Add();
        add.getOperands().add(new Literal(2));
        add.getOperands().add(new Literal(1));
        operands.add(add);
        equals.getOperands().addAll(operands);
        assert equals.validate();
        assert equals.accept(evaluationVisitor) == 1;
    }

    @Test
    void testInvalidEqualsStructureWithNullOperand() {
        Equals equals = new Equals();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(3));
        operands.add(null);
        equals.getOperands().addAll(operands);
        assert !equals.validate();
    }

    @Test
    void testInvalidEqualsStructureWithLessThanTwoOperands() {
        Equals equals = new Equals();
        List<Node> operands = new ArrayList<>();
        assert !equals.validate();
        operands.add(new Literal(3));
        equals.getOperands().addAll(operands);
        assert !equals.validate();
    }

}