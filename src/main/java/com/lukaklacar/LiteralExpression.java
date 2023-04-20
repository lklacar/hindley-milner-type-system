package com.lukaklacar;

import lombok.Data;

@Data
public class LiteralExpression extends Expression {
    private Type value;

    public LiteralExpression(Type value) {
        this.value = value;
    }
}