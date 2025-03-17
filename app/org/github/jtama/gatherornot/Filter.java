package org.github.jtama.gatherornot;

import java.util.function.Predicate;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;

public class Filter {
    public static <T> Gatherer<T, ?, T> filter(Predicate<T> filter) {
        return Gatherer.of(
                Integrator.ofGreedy(
                    (_, item, downstream) -> {
                        if (filter.test(item)) {
                            return downstream.push(item);
                        }
                        return true;
                    }));
    }
}
