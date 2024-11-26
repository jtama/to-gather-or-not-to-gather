///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 23+
//COMPILE_OPTIONS --enable-preview --release 23
//RUNTIME_OPTIONS --enable-preview
//DEPS de.vandermeer:asciitable:0.3.2
//SOURCES org/github/jtama/gatherornot/Merge.java
//SOURCES org/github/jtama/gatherornot/Oeuvre.java
//SOURCES org/github/jtama/gatherornot/Reader.java
//SOURCES org/github/jtama/gatherornot/Tuple.java

import de.vandermeer.asciitable.AsciiTable;

import org.github.jtama.gatherornot.Oeuvre;
import org.github.jtama.gatherornot.Reader;
import org.github.jtama.gatherornot.Tuple;

import java.io.IOException;
import java.util.stream.Stream;

import static org.github.jtama.gatherornot.Merge.merge;

public void main() throws IOException {
    Stream<Oeuvre> oeuvres = Reader.read().stream();
    prettyPrint(oeuvres.gather(merge(streamToBeMerged)));
}

void prettyPrint(Stream<Tuple<String,Oeuvre>> tuples) {
    AsciiTable at = new AsciiTable();
    at.addRule();
    at.addRow("Revue de presse","Année", "Titre");
    at.addRule();
    System.out.println();
    tuples.toList().forEach(tuple -> {
            at.addRow(tuple.first(), tuple.second().anneeParution(), tuple.second().titre());
            at.addRule();
    });
    System.out.println(at.render());
}

private static Stream<String> streamToBeMerged = Stream.of(
        "Don't remember.",
        "Never heard of it.",
        "Top of the charts during 7 weeks",
        "Best play ever"
);


