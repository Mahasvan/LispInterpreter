package com.mahasvan.interpreter.types.visitors;

import com.mahasvan.interpreter.types.nodes.Literal;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.nodes.Variable;
import com.mahasvan.interpreter.types.operators.*;

public class EvaluationVisitor implements NodeVisitor<Integer> {
    @Override
    public Integer visitLiteral(Literal literal) {
        return literal.value;
    }

    @Override
    public Integer visitVariable(Variable variable) {
        return variable.value.accept(this);
    }

    @Override
    public Integer visitAdd(Add add) {
        Integer res = 0;
        for (Node operand : add.getOperands()) {
            res += operand.accept(this);
        }
        return res;
    }

    @Override
    public Integer visitMultiply(Multiply multiply) {
        Integer res = 1;
        for (Node operand : multiply.getOperands()) {
            res *= operand.accept(this);
        }
        return res;
    }

    @Override
    public Integer visitDivide(Divide divide) {
        Double res = null;
        for (Node operand : divide.getOperands()) {
            if (res == null) {
                res = Double.valueOf(operand.accept(this));
            } else {
                res /= Double.valueOf(operand.accept(this));
            }
        }
        if (res == null) return 0;
        return (int) Math.floor(res);
    }

    @Override
    public Integer visitSubtract(Subtract subtract) {
        Integer res = null;
        for (Node operand : subtract.getOperands()) {
            if (res == null) {
                res = operand.accept(this);
            } else {
                res -= operand.accept(this);
            }
        }
        return res;
    }

    @Override
    public Integer visitDefVar(DefVar defVar) {
        Variable var = (Variable) defVar.getOperands().getFirst();
        var.value = defVar.getOperands().get(1);
        return var.value.accept(this);
    }

    @Override
    public Integer visitGreaterThan(GreaterThan greaterThan) {
        Integer val1 = greaterThan.operands.getFirst().accept(this);
        Integer val2 = greaterThan.operands.get(1).accept(this);
        return val1 > val2 ? 1 : 0;
    }

    @Override
    public Integer visitLessThan(LessThan lessThan) {
        Integer val1 = lessThan.operands.getFirst().accept(this);
        Integer val2 = lessThan.operands.get(1).accept(this);
        return val1 < val2 ? 1 : 0;
    }

    @Override
    public Integer visitEquals(Equals equals) {
        Integer val1 = equals.operands.getFirst().accept(this);
        Integer val2 = equals.operands.get(1).accept(this);
        return val1.equals(val2) ? 1 : 0;
    }

    @Override
    public Integer visitIfStatement(IfStatement ifStatement) {
        Integer condition = ifStatement.operands.getFirst().accept(this);
        if (condition != 0) {
            return ifStatement.operands.get(1).accept(this);
        } else if (ifStatement.operands.size() == 3) {
            return ifStatement.operands.get(2).accept(this);
        }
        return 0;
    }


}
