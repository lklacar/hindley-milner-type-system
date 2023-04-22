package com.lukaklacar;

import java.util.HashMap;

public class Unifier {

    public Substitution unify(Type t1, Type t2) {
        if (t1.equals(t2)) {
            return new Substitution(new HashMap<>());
        }
        if (t1 instanceof TypeVariable t1TypeVariable) {
            return unifyVar(t1TypeVariable, t2);
        } else if (t2 instanceof TypeVariable t2TypeVariable) {
            return unifyVar(t2TypeVariable, t1);
        } else if (t1 instanceof FunctionType ft1 && t2 instanceof FunctionType ft2) {
            var s1 = unify(ft1.getInput(), ft2.getInput());
            var s2 = unify(s1.apply(ft1.getOutput()), s1.apply(ft2.getOutput()));
            return s1.compose(s2);
        } else {
            throw new UnificationException("Cannot unify types: " + t1 + " and " + t2);
        }
    }

    private Substitution unifyVar(TypeVariable var, Type type) {
        if (type instanceof TypeVariable && var.getName().equals(((TypeVariable) type).getName())) {
            return new Substitution(new HashMap<>());
        } else if (occursIn(var, type)) {
            throw new UnificationException("Recursive unification: " + var + " occurs in " + type);
        } else {
            var newSubstitution = new HashMap<String, Type>();
            newSubstitution.put(var.getName(), type);
            return new Substitution(newSubstitution);
        }
    }

    private boolean occursIn(TypeVariable var, Type type) {
        if (type instanceof TypeVariable) {
            return var.getName().equals(((TypeVariable) type).getName());
        } else if (type instanceof FunctionType functionType) {
            return occursIn(var, functionType.getInput()) || occursIn(var, functionType.getOutput());
        } else {
            return false;
        }
    }
}
