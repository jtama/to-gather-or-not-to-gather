package org.github.jtama.gatherornot;

import java.util.function.Function;
import java.util.stream.Gatherer;

public class Map {

    public static <T,U> Gatherer<T, ?, U> map(Function<T, U> mapper) {
        return Gatherer.of(
                Gatherer.Integrator.ofGreedy((_, item, downstream) -> downstream.push(mapper.apply(item)))
        );
    }


    public static <T,U> Gatherer<T, ?, U> mapSneakyThrowing(ThrowingFunction<T, U> mapper) {
        return Gatherer.of(Gatherer.Integrator.ofGreedy(
                (_, item, downstream) -> {
                    try {
                        return downstream.push(mapper.apply(item));
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
