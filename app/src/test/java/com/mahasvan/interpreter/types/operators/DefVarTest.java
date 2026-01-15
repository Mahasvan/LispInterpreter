package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.nodes.Variable;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DefVarTest {
    private EvaluationVisitor evaluationVisitor;


    @BeforeEach
    void init() {
        this.evaluationVisitor = new EvaluationVisitor();
    }

    @Test
    void testValidStructureValidatesToTrue() {
        DefVar defVar = new DefVar();
        List<Node> operands = new ArrayList<>();
        Variable a = new Variable();
        Literal one = new Literal(1);
        operands.add(a);
        operands.add(one);
        defVar.getOperands().addAll(operands);
        assert defVar.validate();
        assert defVar.accept(evaluationVisitor) == one.value;
    }

    @Test
    void testInvalidStructureWithLessThanTwoOperands() {
        DefVar defVar = new DefVar();
        List<Node> operands = new ArrayList<>();
        Variable a = new Variable();
        operands.add(a);
        defVar.getOperands().addAll(operands);
        assert !defVar.validate();
    }

    @Test
    void testInvalidStructureWithFirstOperandNotVariable() {
        DefVar defVar = new DefVar();
        List<Node> operands = new ArrayList<>();
        Literal one = new Literal(1);
        operands.add(one);
        defVar.getOperands().addAll(operands);
        assert !defVar.validate();
    }

    @Test
    void testInvalidStructureWithNullOperand() {
        DefVar defVar = new DefVar();
        List<Node> operands = new ArrayList<>();
        Variable a = new Variable();

        operands.add(a);
        operands.add(null);
        defVar.getOperands().addAll(operands);
        assert !defVar.validate();
    }
}