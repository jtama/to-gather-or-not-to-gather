package org.github.jtama.gatherornot;

import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;

public class WithIndex {

    public static Gatherer<Oeuvre, Counting, Tuple<Integer, Oeuvre>> withIndex() {

        return Gatherer.ofSequential(
                Counting::new,
                Integrator.ofGreedy((state, person, downstream) -> downstream.push(new Tuple<>(state.index++, person))));
    }

    static class Counting {
        int index;
    }
}
