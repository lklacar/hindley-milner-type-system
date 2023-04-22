package com.lukaklacar;

public class PrettyPrinter {
    public static String prettyPrint(Type expression) {
        return switch (expression) {
            case TypeVariable typeVariable -> typeVariable.getName();
            case FunctionType functionType -> String.format("(%s -> %s)", prettyPrint(functionType.getInput()), prettyPrint(functionType.getOutput()));
            case TypeConstructor typeConstructor -> typeConstructor.getName();
            default -> throw new IllegalStateException("Unexpected value: " + expression);
        };
    }
}
