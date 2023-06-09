package com.lukaklacar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class FunctionType extends Type {
    private final Type input;
    private final Type output;
}