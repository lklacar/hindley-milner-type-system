package com.lukaklacar;

public class Main {
    public static void main(String[] args) {
        var intType = new TypeConstructor("Int");
        var boolType = new TypeConstructor("Bool");
        var inference = new TypeInference();

        var identity = new Abstraction("x", new Variable("x"));
        var identityType = inference.infer(identity);
        System.out.println("Identity function type: " + identityType);

        var constFunction = new Abstraction("x", new Abstraction("y", new Variable("x")));
        var constFunctionType = inference.infer(constFunction);
        System.out.println("Const function type: " + constFunctionType);

        var application = new Application(identity, new LiteralExpression(intType));
        var applicationType = inference.infer(application);
        System.out.println("Application type: " + applicationType);

        var letExpression = new LetBindingExpression("id", identity, new Application(new Variable("id"), new LiteralExpression(applicationType)));
        var letExpressionType = inference.infer(letExpression);
        System.out.println("Let expression type: " + letExpressionType);

        var ifElseExpression = new IfElse(
                new Variable("x"),
                new LiteralExpression(intType),
                new LiteralExpression(intType)
        );
        var ifElseAbstraction = new Abstraction("x", ifElseExpression);
        var ifElseType = inference.infer(ifElseAbstraction);
        System.out.println("IfElse type: " + ifElseType);

    }
}