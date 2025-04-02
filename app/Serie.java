///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 24+
//COMPILE_OPTIONS --enable-preview --release 24
//RUNTIME_OPTIONS --enable-preview
//DEPS de.vandermeer:asciitable:0.3.2
//SOURCES org/github/jtama/gatherornot/Serie.java
//SOURCES org/github/jtama/gatherornot/Reader.java
//SOURCES org/github/jtama/gatherornot/Oeuvre.java

import de.vandermeer.asciitable.AsciiTable;

import de.vandermeer.asciithemes.a8.A8_Grids;
import org.github.jtama.gatherornot.Oeuvre;
import org.github.jtama.gatherornot.Reader;

import java.io.IOException;
import java.util.stream.Stream;

import static org.github.jtama.gatherornot.Serie.series;

public void main() throws IOException {
    Stream<Oeuvre> oeuvres = Reader.read().stream();
    prettyPrint(oeuvres.gather(series(Oeuvre::anneeParution)));
}

void prettyPrint(Stream<List<Oeuvre>> oeuvresByYear) {
    AsciiTable at = new AsciiTable();
    at.addRule();
    at.addRow("Année", "Titre");
    at.addRule();
    at.getContext().setGrid(A8_Grids.lineDoubleBlocks());
    System.out.println();
    oeuvresByYear.toList().forEach(oeuvres -> {
        oeuvres.forEach(oeuvre -> {
            at.addRow(oeuvre.anneeParution(), oeuvre.titre());
            at.addRule();
        });
        at.addHeavyRule();
    });
    System.out.println(at.render());
}


