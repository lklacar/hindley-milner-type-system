package com.lukaklacar;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Unifier {

    @Getter
    private Map<String, Type> substitutions;

    public Unifier() {
        this.substitutions = new HashMap<>();
    }

    public void unify(Type t1, Type t2) {
        if (t1.equals(t2)) {
            return;
        }

        if (t1 instanceof TypeVariable) {
            unifyVar((TypeVariable) t1, t2);
        } else if (t2 instanceof TypeVariable) {
            unifyVar((TypeVariable) t2, t1);
        } else if (t1 instanceof FunctionType && t2 instanceof FunctionType) {
            FunctionType ft1 = (FunctionType) t1;
            FunctionType ft2 = (FunctionType) t2;
            unify(ft1.getInput(), ft2.getInput());
            unify(ft1.getOutput(), ft2.getOutput());
        } else {
            throw new UnificationException("Cannot unify types: " + t1 + " and " + t2);
        }
    }

    private void unifyVar(TypeVariable var, Type type) {
        if (substitutions.containsKey(var.getName())) {
            unify(substitutions.get(var.getName()), type);
        } else if (type instanceof TypeVariable && substitutions.containsKey(((TypeVariable) type).getName())) {
            unify(var, substitutions.get(((TypeVariable) type).getName()));
        } else if (occursIn(var, type)) {
            throw new UnificationException("Recursive unification: " + var + " occurs in " + type);
        } else {
            substitutions.put(var.getName(), type);
        }
    }

    private boolean occursIn(TypeVariable var, Type type) {
        if (type instanceof TypeVariable) {
            return var.getName().equals(((TypeVariable) type).getName());
        } else if (type instanceof FunctionType) {
            FunctionType funcType = (FunctionType) type;
            return occursIn(var, funcType.getInput()) || occursIn(var, funcType.getOutput());
        } else {
            return false;
        }
    }

    public Type applySubstitutions(Type type) {
        if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            return substitutions.getOrDefault(typeVariable.getName(), typeVariable);
        } else if (type instanceof FunctionType) {
            FunctionType funcType = (FunctionType) type;
            return new FunctionType(
                    applySubstitutions(funcType.getInput()),
                    applySubstitutions(funcType.getOutput())
            );
        } else {
            return type;
        }
    }


}
