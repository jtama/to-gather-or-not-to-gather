package org.github.jtama.gatherornot;

import java.util.function.Predicate;
import java.util.stream.Gatherer;

public class TakeUntil {
    public static Gatherer<Oeuvre, ?, Oeuvre> takeUntil(Predicate<Oeuvre> filter){
        return Gatherer.of((_, person, downstream) -> downstream.push(person) && !filter.test(person));
    }
}
