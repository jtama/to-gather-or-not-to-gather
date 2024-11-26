package org.github.jtama.gatherornot;

import java.util.function.Function;
import java.util.stream.Gatherer;

public class Map {

    public static Gatherer<Oeuvre, ?, String> map(Function<Oeuvre, String> mapper) {
        return Gatherer.of(
                Gatherer.Integrator.ofGreedy((_, person, downstream) -> downstream.push(mapper.apply(person)))
        );
    }


    public static Gatherer<Oeuvre, ?, String> mapSneakyThrowing(ThrowingFunction<Oeuvre, String> mapper) {
        return Gatherer.of(Gatherer.Integrator.ofGreedy(
                (_, person, downstream) -> {
                    try {
                        return downstream.push(mapper.apply(person));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    @FunctionalInterface
    public interface ThrowingFunction<I, R> {
        /**
         * Applies this function to the given argument.
         *
         * @param i the function argument
         * @return the function result
         */
        R apply(I input) throws Exception;

    }
}
