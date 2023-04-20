package com.lukaklacar;

import lombok.Data;

@Data
public class TypeConstructor extends Type {
    private String name;

    public TypeConstructor(String name) {
        this.name = name;
    }
}