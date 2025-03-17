package org.github.jtama.gatherornot;

import java.util.function.Predicate;
import java.util.stream.Gatherer;

public class TakeUntil {
    public static <T> Gatherer<T, ?, T> takeUntil(Predicate<T> filter){
        return Gatherer.of((_, item, downstream) -> downstream.push(item) && !filter.test(item));
    }
}
