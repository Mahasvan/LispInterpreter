package com.mahasvan.interpreter.lexer;

import com.mahasvan.interpreter.types.nodes.Variable;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private static final SymbolTable instance = new SymbolTable();
    Map<String, Variable> table = new HashMap<>();

    private SymbolTable() {
    }

    public static SymbolTable getInstance() {
        return instance;
    }

    public Variable lookup(String name) {
        return table.get(name);
    }

    public void update(String name, Variable value) {
        table.put(name, value);
    }
}
