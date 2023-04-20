package com.lukaklacar;

import java.util.HashMap;
import java.util.Map;

public class TypeInferer {
    private int varCounter;
    private Map<String, Type> context;

    public TypeInferer() {
        this.varCounter = 0;
        this.context = new HashMap<>();
    }

    private TypeVariable freshTypeVar() {
        return new TypeVariable("t" + varCounter++);
    }

    public Type infer(Expression expression) throws UnificationException {
        if (expression instanceof Variable) {
            Variable variable = (Variable) expression;
            if (context.containsKey(variable.getName())) {
                return context.get(variable.getName());
            } else {
                throw new UnificationException("Unbound variable: " + variable.getName());
            }
        } else if (expression instanceof LiteralExpression) {
            return ((LiteralExpression) expression).getValue();
        } else if (expression instanceof Abstraction) {
            Abstraction abstraction = (Abstraction) expression;
            TypeVariable paramType = freshTypeVar();
            context.put(abstraction.getParamName(), paramType);
            Type bodyType = infer(abstraction.getBody());
            context.remove(abstraction.getParamName());
            return new FunctionType(paramType, bodyType);
        } else if (expression instanceof Application) {
            Application application = (Application) expression;
            Type funcType = infer(application.getFunc());
            Type argType = infer(application.getArg());
            TypeVariable resultType = freshTypeVar();
            Unifier unifier = new Unifier();
            unifier.unify(funcType, new FunctionType(argType, resultType));
            return unifier.applySubstitutions(resultType);
        } else if (expression instanceof LetBindingExpression) {
            LetBindingExpression letBindingExpression = (LetBindingExpression) expression;
            Type bindingType = infer(letBindingExpression.getBindingExpression());
            context.put(letBindingExpression.getVarName(), bindingType);
            Type bodyType = infer(letBindingExpression.getBody());
            context.remove(letBindingExpression.getVarName());
            return bodyType;

        } else {
            throw new UnificationException("Unknown expression type: " + expression.getClass());
        }
    }
}
