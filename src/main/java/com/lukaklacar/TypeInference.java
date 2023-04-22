package com.lukaklacar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TypeInference {
    private final Unifier unifier;
    private Iterator<Integer> variableCounter;
    private final Map<String, Type> context;

    public TypeInference() {
        this.context = new HashMap<>();
        this.unifier = new Unifier();
        this.variableCounter = Stream.iterate(1, i -> i + 1).iterator();

    }

    private TypeVariable freshTypeVar() {
        return new TypeVariable("t%d".formatted(variableCounter.next()));
    }

    public Type infer(Expression expression) {
        variableCounter = Stream.iterate(1, i -> i + 1).iterator();
        return infer(expression, new Substitution(new HashMap<>())).type();
    }

    private InferResult infer(Expression expression, Substitution substitution) {
        return switch (expression) {
            case Variable variable -> Optional.ofNullable(context.get(variable.getName()))
                    .map(type -> new InferResult(type, substitution))
                    .orElseThrow(() -> new UnificationException("Unbound variable: " + variable.getName()));
            case LiteralExpression literalExpression -> new InferResult(literalExpression.getValue(), substitution);
            case Abstraction abstraction -> {
                var paramType = freshTypeVar();
                context.put(abstraction.getParamName(), paramType);
                var bodyInferResult = infer(abstraction.getBody(), substitution);
                context.remove(abstraction.getParamName());
                var newSubstitution = substitution.compose(bodyInferResult.substitution());
                yield new InferResult(new FunctionType(
                        newSubstitution.apply(paramType),
                        newSubstitution.apply(bodyInferResult.type())
                ), newSubstitution);
            }
            case Application application -> {
                var funcInferenceResult = infer(application.getFunc(), substitution);
                var argInferenceResult = infer(application.getArg(), substitution);
                var resultType = freshTypeVar();
                var unificationSubst = unifier.unify(funcInferenceResult.type(), new FunctionType(argInferenceResult.type(), resultType));
                yield new InferResult(
                        unificationSubst.apply(resultType),
                        unificationSubst.compose(funcInferenceResult.substitution()).compose(argInferenceResult.substitution())
                );
            }
            case LetBindingExpression letBindingExpression -> {
                var bindingInferenceResult = infer(letBindingExpression.getBindingExpression(), substitution);
                context.put(letBindingExpression.getVarName(), bindingInferenceResult.type());
                var bodyInferenceResult = infer(letBindingExpression.getBody(), substitution);
                context.remove(letBindingExpression.getVarName());
                yield new InferResult(
                        bodyInferenceResult.type(),
                        bodyInferenceResult.substitution().compose(bindingInferenceResult.substitution())
                );
            }
            case IfElse ifElse -> {
                var conditionInferenceResult = infer(ifElse.getCondition(), substitution);
                var thenInferenceResult = infer(ifElse.getThenBranch(), substitution);
                var elseInferenceResult = infer(ifElse.getElseBranch(), substitution);
                var conditionSubst = unifier.unify(conditionInferenceResult.type(), new TypeConstructor("Bool"));
                var thenElseSubst = unifier.unify(thenInferenceResult.type(), elseInferenceResult.type());
                var combinedSubst = conditionSubst.compose(thenElseSubst);
                var resultType = combinedSubst.apply(thenInferenceResult.type());
                yield new InferResult(
                        resultType,
                        combinedSubst.compose(conditionInferenceResult.substitution())
                                .compose(thenInferenceResult.substitution())
                                .compose(elseInferenceResult.substitution())
                );
            }
            default -> throw new UnificationException("Unknown expression type: " + expression.getClass());
        };
    }


}
