package com.lukaklacar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class Application extends Expression {
    private final Expression func;
    private final Expression arg;
}