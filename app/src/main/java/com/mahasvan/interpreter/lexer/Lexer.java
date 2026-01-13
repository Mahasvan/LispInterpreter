package com.mahasvan.interpreter.lexer;

import com.mahasvan.interpreter.types.nodes.*;
import com.mahasvan.interpreter.types.operators.*;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final SymbolTable symbolTable;

    public Lexer() {
        symbolTable = SymbolTable.getInstance();
    }

    private Variable getVariable(String name, boolean isDefVar) {
        Variable var = symbolTable.lookup(name);
        if (isDefVar && var == null) {
            var = new Variable();
            var.name = name;
            symbolTable.update(name, var);
        }
        if (var == null) return null;
        var.name = name;
        return var;
    }

    private boolean checkIfInteger(String input) {
        boolean isNumber = true;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c) && c != '-' && c != '+') {
                isNumber = false;
                break;
            }
        }
        return isNumber;
    }

    private boolean checkIfVariable(String input) {
        boolean isChar = true;
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                isChar = false;
                break;
            }
        }
        return isChar;
    }

    private Node classifyToken(String input, boolean isDefVar) {
        // open parantheses
        // close parantheses
        // arithmetic
        // variable
        // number

        switch (input.toLowerCase()) {
            case "(":
                return new P_OPEN();
            case ")":
                return new P_CLOSE();
            case "+":
                return new Add();
            case "-":
                return new Subtract();
            case "/":
                return new Divide();
            case "*":
                return new Multiply();
            case "defvar":
                return new DefVar();
            case ">":
                return new GreaterThan();
            case "<":
                return new LessThan();
            case "=":
                return new Equals();
            case "if":
                return new IfStatement();
            default: {
                if (checkIfInteger(input)) {
                    return new Literal(Integer.parseInt(input));
                }
                if (checkIfVariable(input)) {
                    return getVariable(input, isDefVar);
                }
            }
        }
        return null;
    }

    public List<Node> tokenize(String input) {
        // Remove extra spaces between tokens, and strip spaces at the start and end.
        input = input.replaceAll(" {2,}", " ").strip();

        String[] stringTokens = input.split(" ");
        boolean isDefVar = false;
        for (String stringToken : stringTokens) {
            if (stringToken.equals("defvar")) {
                isDefVar = true;
                break;
            }
        }
        ArrayList<Node> tokens = new ArrayList<>();
        for (String stringToken : stringTokens) {
            Node token = classifyToken(stringToken, isDefVar);
            if (token == null) {
                throw new ArithmeticException("Invalid token: " + stringToken);
            }
            tokens.add(token);
        }
        return tokens;
    }
}
