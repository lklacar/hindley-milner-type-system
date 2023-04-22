# Hindley-Milner Type Inference in Java

This project demonstrates a basic implementation of the Hindley-Milner type system and type inference algorithm in Java.
It uses a simple expression language, which includes variables, literals, lambda abstractions, function applications,
and let bindings.

## Hindley-Milner Type System and Type Inference

The Hindley-Milner (HM) type system is a powerful and expressive type system widely used in functional programming languages such as Haskell and ML. One of its key features is the ability to automatically infer types of expressions without the need for explicit type annotations, making it easier for programmers to write concise code while maintaining type safety.

This implementation focuses on a simple expression language based on lambda calculus, extended with polymorphic types and let bindings. The type inference algorithm, known as Algorithm W, utilizes unification to ensure the consistency of types throughout the program.

### Key Concepts

- **Lambda Calculus**: The foundation of the HM type system, lambda calculus is a minimalistic formalism for expressing computation using functions. It serves as a foundation for functional programming and type systems.

- **Polymorphic Types**: A core feature of the HM type system, polymorphic types enable functions to work with multiple input types, making the type system more flexible and expressive.

- **Unification**: A technique for finding a substitution that makes two types equal, unification is central to the HM type inference algorithm. It can be thought of as solving a system of equations over types.

- **Type Inference Algorithm**: The algorithm, known as Algorithm W, traverses the abstract syntax tree (AST) of a program, generating type equations for each node. Unification is then used to solve these equations, resulting in type substitutions that ensure consistency across the program.

### Benefits of Hindley-Milner Type System

The main advantage of the HM type system lies in its ability to automatically infer types for complex programs without requiring explicit type annotations. This allows programmers to focus on the logic of their programs rather than managing types. The type inference algorithm guarantees that if a program can be assigned a type, it will not have any type-related runtime errors, providing strong safety guarantees and helping catch many bugs at compile time.

By using the Hindley-Milner type system, developers can create concise, safe, and expressive code, making it a popular choice for functional programming languages.

## Features

- Type inference for lambda calculus expressions extended with let bindings
- Unification-based type inference using the Hindley-Milner algorithm
- Simple expression language with support for variables, literals, abstractions, applications, and let bindings

## Getting Started

To use the project, simply clone the repository and import it into your favorite Java IDE.

### Prerequisites

- Java 8 or higher
- Lombok library (for concise class definitions)

### Usage

Here's an example that demonstrates how to create expressions and infer their types using the `TypeInferer` class:

```java
public class Main {
    public static void main(String[] args) {
        var inference = new TypeInference();

        // Identity function: λx.x
        var identity = new Abstraction("x", new Variable("x"));
        var identityType = inference.infer(identity);
        System.out.println("Identity function type: " + PrettyPrinter.prettyPrint(identityType));

        // Constant function: λx.λy.x
        var constFunction = new Abstraction("x", new Abstraction("y", new Variable("x")));
        var constFunctionType = inference.infer(constFunction);
        System.out.println("Constant function type: " + PrettyPrinter.prettyPrint(constFunctionType));

        // Application: (λx.x) 1
        var application = new Application(identity, new LiteralExpression(new TypeConstructor("Int")));
        var applicationType = inference.infer(application);
        System.out.println("Application type: " + PrettyPrinter.prettyPrint(applicationType));

        // Let-binding: let id = λx.x in id 2
        var letExpression = new LetBindingExpression("id", identity, new Application(new Variable("id"), new LiteralExpression(new TypeConstructor("Int"))));
        var letExpressionType = inference.infer(letExpression);
        System.out.println("Let-binding expression type: " + PrettyPrinter.prettyPrint(letExpressionType));

        // If-else expression: λx.if x then 1 else 2
        var ifElseExpression = new IfElse(
                new Variable("x"),
                new LiteralExpression(new TypeConstructor("Int")),
                new LiteralExpression(new TypeConstructor("Int"))
        );
        var ifElseAbstraction = new Abstraction("x", ifElseExpression);
        var ifElseType = inference.infer(ifElseAbstraction);
        System.out.println("If-else expression type: " + PrettyPrinter.prettyPrint(ifElseType));

        // Successor function: λn.λs.λz.s (n s z)
        var successor = new Abstraction("n", new Abstraction("s", new Abstraction("z", new Application(new Variable("s"), new Application(new Application(new Variable("n"), new Variable("s")), new Variable("z"))))));
        var successorType = inference.infer(successor);
        System.out.println("Successor function type: " + PrettyPrinter.prettyPrint(successorType));

        // Multiplication function: λx.λy.λs.λz.x (y s) (z s)
        var multiplication = new Abstraction("x", new Abstraction("y", new Abstraction("s", new Abstraction("z", new Application(new Application(new Variable("x"), new Application(new Variable("y"), new Variable("s"))), new Application(new Variable("z"), new Variable("s")))))));
        var multiplicationType = inference.infer(multiplication);
        System.out.println("Multiplication function type: " + PrettyPrinter.prettyPrint(multiplicationType));

        // Pair function: λx.λy.λz.z x y
        var pair = new Abstraction("x", new Abstraction("y", new Abstraction("z", new Application(new Application(new Variable("z"), new Variable("x")), new Variable("y")))));
        var pairType = inference.infer(pair);
        System.out.println("Pair function type: " + PrettyPrinter.prettyPrint(pairType));

        // First projection: λp.p (λx.λy.x)
        var firstProjection = new Abstraction("p", new Application(new Variable("p"), new Abstraction("x", new Abstraction("y", new Variable("x")))));
        var firstProjectionType = inference.infer(firstProjection);
        System.out.println("First projection type: " + PrettyPrinter.prettyPrint(firstProjectionType));

        // Second projection: λp.p (λx.λy.y)
        var secondProjection = new Abstraction("p", new Application(new Variable("p"), new Abstraction("x", new Abstraction("y", new Variable("y")))));
        var secondProjectionType = inference.infer(secondProjection);
        System.out.println("Second projection type: " + PrettyPrinter.prettyPrint(secondProjectionType));
    }
}
```

## Future Enhancements

There are several additional features you might want to consider implementing to make the system more robust and
expressive:

- Built-in types and functions
- Type annotations
- Polymorphic types
- Algebraic data types
- Error reporting
- Recursive functions
- Module system
- Type classes

## Acknowledgments

- Hindley-Milner type system and Algorithm W
- Lombok library for reducing boilerplate code in Java
