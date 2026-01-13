package com.mahasvan.interpreter.types.nodes;

import com.mahasvan.interpreter.types.visitors.NodeVisitor;

public interface Node {
    <T> T accept(NodeVisitor<T> visitor);

    boolean validate();
}
