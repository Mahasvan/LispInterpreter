package com.mahasvan.interpreter.types.nodes;

import com.mahasvan.interpreter.types.visitors.NodeVisitor;

public class Variable implements Node {

    public String name;
    public Node value;

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public boolean validate() {
        return true;
    }
}
