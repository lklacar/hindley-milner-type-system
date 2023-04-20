package com.lukaklacar;

import lombok.Data;

@Data
public class FunctionType extends Type {
    private Type input;
    private Type output;

    public FunctionType(Type input, Type output) {
        this.input = input;
        this.output = output;
    }
}