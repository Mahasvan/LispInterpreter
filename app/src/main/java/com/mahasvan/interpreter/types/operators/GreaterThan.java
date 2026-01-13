package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class GreaterThan implements Operator {
    public List<Node> operands = new ArrayList<>();

    @Override
    public List<Node> getOperands() {
        return operands;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitGreaterThan(this);
    }

    @Override
    public boolean validate() {
        if (operands.size() != 2) return false;
        for (Node operand : operands) {
            if (operand == null) return false;
        }
        return true;
    }
}
