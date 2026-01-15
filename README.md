# LispInterpreter

### REPL for Lisp, written in Java.

Supports the following capabilities:

- Arithmetic operations - ` + - / * `
    - operations are evaluated left to right, with precedence given to braces above all else:
        - `( + a b c )` --> `a + b + c`
        - `( / a b c )` --> `a / b / c` (evaluated left to right)
        - `( * 2 ( - 3 4 )` --> `2 * (3-4)` --> `2 * -1` --> `-1`
- Variable definition - `( defvar myVar {expression} )`
    - Variables are case-sensitive.
- If statements - `( if {condition} {true-branch} {false-branch} )`
    - Example: `( if ( < a 0 ) 10 20 )`
    - If `{condition}` is truthy (value != 0), the true branch is executed.
    - If it is falsy (value == 0), the else condition is executed.
        - If there is no else condition, `0` is returned

### Note

A space must separate every token.

This is not Lisp standard, but this simplifies the lexer greatly,
because this way I don't need to deal with stuff like maximal munching.

## Code examples

```shell
>>> ( defvar a 1 )
1
>>> ( defvar b 2 )
2
>>> ( + a b )
3
>>> ( defvar c ( + a b ) )
3
>>> ( * c b )
6
>>> ( if ( = c 2 ) ( defvar d 5 ) ( defvar d 6 ) )
6
>>> ( = d 6 )
1
```

## Notes

- ~~Syntax checking has not been implemented yet.~~
- Basic syntax checking is now functional.
    - Each operator has verification rules defined in its `validate()` method.
    - It is not foolproof, but any edge-case errors are caught by the REPL and displayed.
- The symbol table is part of the lexer.
    - the same object is returned by the token factory for the same variable name,
    - so variable reassignments are handled in-place.
- Variable definitions can be treated as normal operations.
    - The Evaluation engine will return the assigned value for the expression.

## Problems

- Variable definitions inside else-statements populate the symbol table before even if the branch isn't executed.
    - Example:
    - ```shell
      >>> ( if 1 ( + 1 2 ) ( defvar a 5 ) )
      3
      >>> ( + 0 a ) <-- Runtime error because value of `a` is null.
      Runtime Error: Cannot invoke "com.mahasvan.interpreter.types.nodes.Node.accept(com.mahasvan.interpreter.types.visitors.NodeVisitor)" because "variable.value" is null
      ```
    - When building the AST, a Variable object is created for `a` naively, as it is inside a `defvar` statement.
    - Since the branch hasnt been executed yet, its value hasn't been assigned yet, so is still `null`.
    - So, when the evaluator tries to evaluate the value of `a`, it errors out. 
    - We can treat it as an expected runtime error, because this is sort of similar to what happens in Python.
    - Fixable creating an `if null` condition in the Variable evaluator, but throwing a runtime error is good enough. 
- ~~When a variable is defined, the value expression is stored as-is.~~
  - **Fixed**. 
  - This means if the value has a variable, and the variable is reassigned, all variables that depend on it will be changed as well.
  - ```shell
    ( defvar a 1 )
    ( defvar b a )
    ( defvar a 3 )
    ( + 0 b ) <-- prints `3`, but should have been `1`
    ```
  - Can be solved by flattening the expression into a `Literal` when assigning the value.


## Patterns used

- Singleton for the symbol table
- Factory in Lexical Analysis
    - return the correct token type based on the string value
    - When lexer encounters a variable, return variable object from the symbol table if it exists already
- Visitor for evaluation

## Todo
- ~~fix variable reassignment problem~~ Fixed.
- unary operations
- ~~unit tests~~ Unit tests are done. 
- `write` operation?