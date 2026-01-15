package com.mahasvan.interpreter.parser;

import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.nodes.PClose;
import com.mahasvan.interpreter.types.nodes.POpen;
import com.mahasvan.interpreter.types.operators.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ASTBuilder {

    private Node tree = null;

    private List<Node> getOperands(Stack<Node> stack) {
        ArrayList<Node> operands = new ArrayList<>();
        while (!stack.isEmpty() && !(stack.peek() instanceof POpen)) {
            operands.add(stack.pop());
        }
        if (!stack.isEmpty()) {
            stack.pop();
            // pop out the open parenthesis
        }
        return operands.reversed();
    }

    private boolean isInvalidSyntax(Node expression) {
        if (!expression.validate()) return true;

        if (expression instanceof Operator operator) {
            for (Node operand : operator.getOperands()) {
                if (isInvalidSyntax(operand)) return true;
            }
        }

        return false;
    }

    public void build(List<Node> tokens) throws ArithmeticException {
        Stack<Node> stack = new Stack<>();
        for (Node token : tokens) {
            if (token instanceof PClose) {
                List<Node> statementTokens = getOperands(stack);
                // in prefix, the first symbol is the operator, rest are operands
                Node op = statementTokens.getFirst();
                if (!(op instanceof Operator operator)) {
                    throw new ArithmeticException("Invalid syntax");
                }
                // add these operands as the children of the operator
                operator.getOperands().addAll(statementTokens.subList(1, statementTokens.size()));
                stack.push(op);
            } else {
                stack.push(token);
            }
        }
//        System.out.println("Stack: " + stack);
        // the hope is that only the root operation will remain in the stack.
        if (stack.size() != 1 || isInvalidSyntax(stack.peek())) {
            throw new ArithmeticException("Invalid syntax");
        }
        this.tree = stack.pop();
    }

    public Node getTree() {
        return tree;
    }
}
