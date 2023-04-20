package com.lukaklacar;

import lombok.Data;

@Data
public class Variable extends Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }
}