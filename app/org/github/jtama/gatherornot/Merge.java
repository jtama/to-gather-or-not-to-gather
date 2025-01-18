package org.github.jtama.gatherornot;

import java.util.Iterator;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Merge {

    public static <T> Gatherer<Oeuvre, Iterator<T>, Tuple<T, Oeuvre>> merge(Stream<T> stream) {
        return Gatherer.ofSequential(
                stream::iterator,
                Gatherer.Integrator.of((state, person, downstream) -> {
                    if (state.hasNext())
                        return downstream.push(new Tuple<>(state.next(), person));
                    return false;
                }),
                Gatherer.defaultFinisher());
    }
}
 