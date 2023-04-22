package com.lukaklacar;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Substitution {
    private final Map<String, Type> substitutions;

    public Substitution(Map<String, Type> substitutions) {
        this.substitutions = substitutions;
    }

    public Type apply(Type type) {
        return switch (type) {
            case TypeVariable typeVariable -> substitutions.getOrDefault(typeVariable.getName(), typeVariable);
            case FunctionType funcType -> new FunctionType(
                    apply(funcType.getInput()),
                    apply(funcType.getOutput())
            );
            default -> type;
        };
    }


    public Substitution compose(Substitution other) {
        Map<String, Type> composed = new HashMap<>(substitutions);
        composed.putAll(other.getSubstitutions());

        return new Substitution(composed);
    }
}
