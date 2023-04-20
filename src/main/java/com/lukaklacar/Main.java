package com.lukaklacar;

public class Main {
    public static void main(String[] args) {
        // Create some types for our literals
        Type intType = new TypeConstructor("Int");
        Type boolType = new TypeConstructor("Bool");

        // Create some expressions to infer types for
        Expression identity = new Abstraction("x", new Variable("x"));
        Expression constFunction = new Abstraction("x", new Abstraction("y", new Variable("x")));
        Expression application = new Application(identity, new LiteralExpression(intType));

        TypeInferer inferer = new TypeInferer();

        try {
            Type identityType = inferer.infer(identity);
            System.out.println("Identity function type: " + identityType);

            Type constFunctionType = inferer.infer(constFunction);
            System.out.println("Const function type: " + constFunctionType);

            Type applicationType = inferer.infer(application);
            System.out.println("Application type: " + applicationType);


            Expression letExpression = new LetBindingExpression("id", identity, new Application(new Variable("id"), new LiteralExpression(applicationType)));

            Type letExpressionType = inferer.infer(letExpression);
            System.out.println("Let expression type: " + letExpressionType);
        } catch (UnificationException e) {
            e.printStackTrace();
        }
    }
}