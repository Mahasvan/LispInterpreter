package com.mahasvan.interpreter.types.visitors;

import com.mahasvan.interpreter.types.nodes.*;
import com.mahasvan.interpreter.types.operators.*;

public interface NodeVisitor<T> {
    T visitLiteral(Literal literal);

    T visitVariable(Variable variable);

    T visitAdd(Add add);

    T visitMultiply(Multiply multiply);

    T visitDivide(Divide divide);

    T visitSubtract(Subtract subtract);

    T visitDefVar(DefVar defVar);

    T visitGreaterThan(GreaterThan greaterThan);

    T visitLessThan(LessThan lessThan);

    T visitEquals(Equals equals);

    T visitIfStatement(IfStatement ifStatement);
}
