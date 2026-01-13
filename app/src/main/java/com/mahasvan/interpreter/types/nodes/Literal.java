package com.mahasvan.interpreter.types.nodes;

import com.mahasvan.interpreter.types.visitors.NodeVisitor;

public class Literal implements Node {

    public int value;

    public Literal(int value) {
        this.value = value;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public boolean validate() {
        return true;
    }
}
