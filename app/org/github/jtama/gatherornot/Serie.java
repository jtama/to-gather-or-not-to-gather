package org.github.jtama.gatherornot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Gatherer;

public class Serie {

    public static <K,V> Gatherer<V, State, List<V>> series(Function<V,K> keyExtractor) {
        return Gatherer.ofSequential(
                State::new,
                Gatherer.Integrator.ofGreedy((state, value, downstream) -> {
                    // First invocation or the same key value
                    if (state.key == null || keyExtractor.apply(value).equals(state.key)) {
                        state.values.add(value);
                        state.key = keyExtractor.apply(value);
                        return true;
                    }
                    var more = downstream.push(state.values);
                    state.values = new ArrayList<>();
                    state.key = keyExtractor.apply(value);
                    state.values.add(value);
                    return more;
                }),
                (state, downstream) -> downstream.push(state.values));
    }

    public static class State<K,V> {
        private K key;
        private List<V> values = new ArrayList<>();
    }
}
