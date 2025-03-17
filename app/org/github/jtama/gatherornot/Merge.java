package org.github.jtama.gatherornot;

import java.util.Iterator;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Merge {

    public static <T, Y> Gatherer<Y, Iterator<T>, Tuple<T, Y>> merge(Stream<T> stream) {
        return Gatherer.ofSequential(
                stream::iterator,
                Gatherer.Integrator.of((state, item, downstream) -> {
                    if (state.hasNext())
                        return downstream.push(new Tuple<>(state.next(), item));
                    return false;
                }));
    }
}