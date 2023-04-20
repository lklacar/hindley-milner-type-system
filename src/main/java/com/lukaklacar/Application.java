package com.lukaklacar;

import lombok.Data;

@Data
public class Application extends Expression {
    private Expression func;
    private Expression arg;

    public Application(Expression func, Expression arg) {
        this.func = func;
        this.arg = arg;
    }
}