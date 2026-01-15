package com.mahasvan.interpreter.types.visitors;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Variable;
import com.mahasvan.interpreter.types.operators.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EvaluationVisitorTest {
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

    @Test
    void testVariableEvaluation() {
        Variable var = new Variable();
        var.value = new Literal(123);
        assertTrue(var.validate());
        assertEquals(123, var.accept(evaluationVisitor));

        Add add = new Add();
        add.getOperands().add(new Literal(1));
        add.getOperands().add(new Literal(2));
        var.value = add;
        assertTrue(var.validate());
        assertEquals(3, var.accept(evaluationVisitor));

    }

    @Test
    void testAdditionEvaluation() {
        Add add = new Add();
        add.getOperands().add(new Literal(1));
        add.getOperands().add(new Literal(1));
        assert add.accept(evaluationVisitor) == 2;
        add.getOperands().add(new Literal(-3));
        assert add.accept(evaluationVisitor) == -1;
    }

    @Test
    void testMultiplyEvaluation() {
        Multiply multiply = new Multiply();
        multiply.getOperands().add(new Literal(2));
        multiply.getOperands().add(new Literal(3));
        assert multiply.accept(evaluationVisitor) == 6;
        multiply.getOperands().add(new Literal(-5));
        assert multiply.accept(evaluationVisitor) == -30;
        multiply.getOperands().add(new Literal(0));
        assert multiply.accept(evaluationVisitor) == 0;
    }

    @Test
    void testDivideEvaluation() {
        Divide divide = new Divide();
        divide.getOperands().add(new Literal(6));
        divide.getOperands().add(new Literal(2));
        assert divide.accept(evaluationVisitor) == 3;
        divide.getOperands().add(new Literal(-3));
        assert divide.accept(evaluationVisitor) == -1;
        // When there are no operands, we return 0.
        // This will never happen, as the validation logic will throw an error before execution.
        divide.getOperands().clear();
        assert divide.accept(evaluationVisitor) == 0;
    }

    @Test
    void testSubtractionEvaluation() {
        Subtract subtract = new Subtract();
        subtract.getOperands().add(new Literal(10));
        subtract.getOperands().add(new Literal(5));
        assert subtract.accept(evaluationVisitor) == 5;
    }

    @Test
    void testDefVarEvaluation() {
        DefVar defVar = new DefVar();
        Variable var = new Variable();
        Literal value = new Literal(1);
        defVar.getOperands().add(var);
        defVar.getOperands().add(value);
        assert var.value == null;
        defVar.accept(evaluationVisitor);
        assert var.value == value;
    }

    @Test
    void testGreaterThanEvaluation() {
        GreaterThan greaterThan = new GreaterThan();
        greaterThan.getOperands().add(new Literal(4));
        greaterThan.getOperands().add(new Literal(3));
        assert greaterThan.accept(evaluationVisitor) == 1;
    }

    @Test
    void testLessThanEvaluation() {
        LessThan lessThan = new LessThan();
        lessThan.getOperands().add(new Literal(4));
        lessThan.getOperands().add(new Literal(3));
        assert lessThan.accept(evaluationVisitor) == 0;
    }

    @Test
    void testEqualsEvaluation() {
        Equals equals = new Equals();
        equals.getOperands().add(new Literal(4));
        equals.getOperands().add(new Literal(4));
        assert equals.accept(evaluationVisitor) == 1;
        equals.getOperands().clear();
        equals.getOperands().add(new Literal(4));
        equals.getOperands().add(new Literal(3));
        assert equals.accept(evaluationVisitor) == 0;
    }

    @Test
    void testIfStatementEvaluation() {
        IfStatement ifStatement = new IfStatement();
        ifStatement.getOperands().add(new Literal(1));
        ifStatement.getOperands().add(new Literal(2));
        // if (1), then (2)
        assert ifStatement.accept(evaluationVisitor) == 2;
        ifStatement.getOperands().add(new Literal(3));
        // if (1), then (2), else (3)
        assert ifStatement.accept(evaluationVisitor) == 2;
        ifStatement.getOperands().removeFirst();
        ifStatement.getOperands().addFirst(new Literal(0));
        // if (0), then (2), else (3)
        assert ifStatement.accept(evaluationVisitor) == 3;

        // When there are more than two branches, the evaluator will return 0.
        // This branch will never get executed practically
        // because the IfStatement validation will not accept three branches.
        ifStatement.getOperands().add(new Literal(1));
        assert  ifStatement.accept(evaluationVisitor) == 0;
    }


}