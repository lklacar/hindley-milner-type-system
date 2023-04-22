package com.lukaklacar;

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