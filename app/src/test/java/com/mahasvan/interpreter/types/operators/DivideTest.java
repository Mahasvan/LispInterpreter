package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DivideTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidDivideStructure() {
        Divide divide = new Divide();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(3));
        operands.add(new Literal(2));
        divide.getOperands().addAll(operands);
        assertTrue(divide.validate());
        assert divide.accept(evaluationVisitor) == 1;
    }

    @Test
    void testInvalidDivideStructureWithNullOperand() {
        Divide divide = new Divide();
        List<Node> operands = new ArrayList<>();
        operands.add(null);
        operands.add(null);
        divide.getOperands().addAll(operands);
        assert !divide.validate();
    }

    @Test
    void testInvalidDivideStructureWithLessThanTwoOperands() {
        Divide divide = new Divide();
        List<Node> operands = new ArrayList<>();
        assert !divide.validate();
        operands.add(new Literal(3));
        divide.getOperands().addAll(operands);
        assert !divide.validate();
    }

}