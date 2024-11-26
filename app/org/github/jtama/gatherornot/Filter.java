package org.github.jtama.gatherornot;

import java.util.function.Predicate;
import java.util.stream.Gatherer;

public class Filter {
    public static Gatherer<Oeuvre, ?, Oeuvre> filter(Predicate<Oeuvre> filter){

        return Gatherer.of(Gatherer.Integrator.ofGreedy((_, person, downstream) -> {
            if(filter.test(person)){
                return downstream.push(person);
            }
            return true;
        }));
    }
}
