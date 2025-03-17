package org.github.jtama.gatherornot;

import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;

public class WithIndex {

    public static <T> Gatherer<T, Counting, Tuple<Integer, T>> withIndex() {

        return Gatherer.ofSequential(
                () -> new Counting(),
                Integrator.ofGreedy((state, item, downstream) -> downstream.push(new Tuple<>(state.index++, item))));
    }

    static class Counting {
        int index;
    }
}

