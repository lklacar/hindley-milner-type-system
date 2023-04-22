package com.lukaklacar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class IfElse extends Expression {
    private final Expression condition;
    private final Expression thenBranch;
    private final Expression elseBranch;

}