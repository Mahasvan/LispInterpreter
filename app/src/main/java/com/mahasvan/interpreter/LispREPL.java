package com.mahasvan.interpreter;

import com.mahasvan.interpreter.lexer.Lexer;
import com.mahasvan.interpreter.parser.ASTBuilder;
import com.mahasvan.interpreter.types.nodes.Node;
import com.mahasvan.interpreter.types.visitors.EvaluationVisitor;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class LispREPL {
    public static void main(String[] args) {
        System.out.println("Input each line, with each token separated by a space.");
        boolean exit = false;
        Lexer lexer = new Lexer();
        ASTBuilder treeBuilder = new ASTBuilder();
        EvaluationVisitor evaluator = new EvaluationVisitor();
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.print(">>> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String input = scanner.nextLine();
            try {
                List<Node> tokens = lexer.tokenize(input);

//                System.out.print("Tokens: ");
//                for (Expression token : tokens) {
//                    System.out.print("[" + token.getClass().getSimpleName() + "] ");
//                }
//                System.out.println();

                treeBuilder.build(tokens);
                System.out.println(treeBuilder.getTree().accept(evaluator));
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Runtime Error: " + e.getMessage());
            }

            if (Objects.equals(input, "exit")) {
                exit = true;
            }
        }
        scanner.close();
    }
}