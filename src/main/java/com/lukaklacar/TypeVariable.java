package com.lukaklacar;

import lombok.Data;

@Data
public class TypeVariable extends Type {
    private String name;

    public TypeVariable(String name) {
        this.name = name;
    }
}
