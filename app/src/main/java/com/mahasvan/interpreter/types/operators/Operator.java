package com.mahasvan.interpreter.types.operators;

import com.mahasvan.interpreter.types.nodes.Node;

import java.util.List;

public interface Operator extends Node {
    List<Node> getOperands();
}
