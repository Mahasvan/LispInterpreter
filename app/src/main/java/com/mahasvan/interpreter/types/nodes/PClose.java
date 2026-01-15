package com.mahasvan.interpreter.types.nodes;

import com.mahasvan.interpreter.types.visitors.NodeVisitor;

public class PClose implements Node {
    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return null;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
