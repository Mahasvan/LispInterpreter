package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IfStatementTest {
    private EvaluationVisitor evaluationVisitor;

    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidStructureWithNoElseCondition() {
        IfStatement ifStatement = new IfStatement();
        List<Node> operands = new ArrayList<>();
        // if (2) then (3)
        // (2) is truthy, so it executes the `then` condition
        operands.add(new Literal(2));
        operands.add(new Literal(3));
        ifStatement.getOperands().addAll(operands);
        assert ifStatement.validate();
        assert ifStatement.accept(evaluationVisitor) == 3;
        // if (2) then (3), else (4)
        // (2) is truthy, so it still will only execute the `then` condition
        operands.add(new Literal(4));
        assert ifStatement.validate();
        assert ifStatement.accept(evaluationVisitor) == 3;
    }

    @Test
    void testValidStructureWithElseCondition() {
        IfStatement ifStatement = new IfStatement();
        List<Node> operands = new ArrayList<>();
        // if (0) then (3), else (4)
        // (0) is falsy, so it executes the else condition
        operands.add(new Literal(0));
        operands.add(new Literal(3));
        operands.add(new Literal(4));
        ifStatement.getOperands().addAll(operands);
        assert ifStatement.validate();
        assert ifStatement.accept(evaluationVisitor) == 4;
    }

    @Test
    void testInvalidStructureWithLessThanTwoOperands() {
        IfStatement ifStatement = new IfStatement();
        assert !ifStatement.validate();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(2));
        ifStatement.getOperands().addAll(operands);
        assert !ifStatement.validate();
    }

    @Test
    void testInvalidStructureWithNullOperand() {
        IfStatement ifStatement = new IfStatement();
        List<Node> operands = new ArrayList<>();
        operands.add(new Literal(2));
        operands.add(null);
        ifStatement.getOperands().addAll(operands);
        assert !ifStatement.validate();
    }



}