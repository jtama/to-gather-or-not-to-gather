package org.github.jtama.gatherornot;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Merge {

    public static <T> Gatherer<Oeuvre, Iterator<T>, Tuple<T, Oeuvre>> merge(Stream<T> stream) {

        return new Gatherer<>() {

            @Override
            public Supplier<Iterator<T>> initializer() {
                return () -> stream.iterator();
            }

            @Override
            public Integrator<Iterator<T>, Oeuvre, Tuple<T, Oeuvre>> integrator() {
                return Integrator.of((state, person, downstream) -> {
                    if (state.hasNext())
                        return downstream.push(new Tuple<>(state.next(), person));
                    return false;
                });
            }
        };
    }
}
