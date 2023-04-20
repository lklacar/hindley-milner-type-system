package com.lukaklacar;

import lombok.Data;

@Data
public class Abstraction extends Expression {
    private String paramName;
    private Expression body;

    public Abstraction(String paramName, Expression body) {
        this.paramName = paramName;
        this.body = body;
    }
}