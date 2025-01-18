///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 23+
//COMPILE_OPTIONS --enable-preview --release 23
//RUNTIME_OPTIONS --enable-preview
//DEPS de.vandermeer:asciitable:0.3.2
//SOURCES org/github/jtama/gatherornot/Oeuvre.java
//SOURCES org/github/jtama/gatherornot/Reader.java
//SOURCES org/github/jtama/gatherornot/Tuple.java
//SOURCES org/github/jtama/gatherornot/WithIndex.java

import de.vandermeer.asciitable.AsciiTable;
import org.github.jtama.gatherornot.Oeuvre;
import org.github.jtama.gatherornot.Reader;
import org.github.jtama.gatherornot.Tuple;

import static org.github.jtama.gatherornot.WithIndex.withIndex;

public void main() throws IOException {
    Stream<Oeuvre> oeuvres = Reader.read().stream();
    AtomicInteger count = new AtomicInteger(0);
    //prettyPrint(oeuvres.map(value -> new Tuple<>(count.getAndIncrement(), value)));
    //prettyPrint(oeuvres.parallel().map(value -> new Tuple<>(count.getAndIncrement(), value)));
    //prettyPrint(oeuvres.gather(withIndex()));
    prettyPrint(oeuvres.parallel().gather(withIndex()));
}

void prettyPrint(Stream<Tuple<Integer, Oeuvre>> tuples) {
    AsciiTable at = new AsciiTable();
    at.addRule();
    at.addRow("N°", "Année", "Titre");
    at.addRule();
    tuples.forEach(tuple -> {
        synchronized (at) {
            var oeuvre = tuple.second();
            at.addRow(tuple.first() == 0 ? "🥇" :
                tuple.first() == 1 ? "🥈" :
                tuple.first() == 2 ? "🥉" : tuple.first(), oeuvre.anneeParution(), oeuvre.titre());
            at.addRule();
        }
    });
    System.out.println(at.render());
}