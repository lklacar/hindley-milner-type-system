package com.lukaklacar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class LetBindingExpression extends Expression {
    private final String varName;
    private final Expression bindingExpression;
    private final Expression body;

}
