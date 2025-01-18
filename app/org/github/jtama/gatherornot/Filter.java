package org.github.jtama.gatherornot;

import java.util.function.Predicate;
import java.util.stream.Gatherer;

public class Filter {
    public static Gatherer<Oeuvre, ?, Oeuvre> filter(Predicate<Oeuvre> filter){
        return new Gatherer<>() {
            @Override
            public Gatherer.Integrator<Object, Oeuvre, Oeuvre> integrator() {
                return new Integrator<>() {
                    @Override
                    public boolean integrate(Object state, Oeuvre oeuvre, Downstream<? super Oeuvre> downstream) {
                        if(filter.test(oeuvre)){
                            return downstream.push(oeuvre);
                        }
                        return true;
                    }
                };
            }
        };
    }
}
