package com.lukaklacar;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LetBindingExpression extends Expression {
    private String varName;
    private Expression bindingExpression;
    private Expression body;

    public LetBindingExpression(String varName, Expression bindingExpression, Expression body) {
        this.varName = varName;
        this.bindingExpression = bindingExpression;
        this.body = body;
    }
}
