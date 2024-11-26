package org.github.jtama.gatherornot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;

public class Serie {

    public static Gatherer<Oeuvre, State, List<Oeuvre>> series() {
        return Gatherer.ofSequential(
                State::new,
                Gatherer.Integrator.ofGreedy((state, oeuvre, downstream) -> {
                    if (state.year == null || oeuvre.anneeParution().equals(state.year)) {
                        state.oeuvres.add(oeuvre);
                        state.year = oeuvre.anneeParution();
                        return true;
                    }
                    var more = downstream.push(state.oeuvres);
                    state.oeuvres = new ArrayList<>();
                    state.year = oeuvre.anneeParution();
                    state.oeuvres.add(oeuvre);
                    return more;
                }),
                (state, downstream) -> downstream.push(state.getClubs()));
    }

    public static class State {
        private Integer year;
        private List<Oeuvre> oeuvres = new ArrayList<>();

        public Integer getYear() {
            return year;
        }

        public List<Oeuvre> getClubs() {
            return oeuvres;
        }
    }
}