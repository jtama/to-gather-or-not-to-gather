///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 24+
//COMPILE_OPTIONS --enable-preview --release 24
//RUNTIME_OPTIONS --enable-preview
//DEPS de.vandermeer:asciitable:0.3.2
//SOURCES org/github/jtama/gatherornot/TakeUntil.java
//SOURCES org/github/jtama/gatherornot/Reader.java
//SOURCES org/github/jtama/gatherornot/Oeuvre.java

import de.vandermeer.asciitable.AsciiTable;
import org.github.jtama.gatherornot.Oeuvre;
import org.github.jtama.gatherornot.Reader;

import static org.github.jtama.gatherornot.TakeUntil.takeUntil;

public void main() throws IOException {
    Stream<Oeuvre> oeuvres = Reader.read().stream();
    prettyPrint(oeuvres.gather(takeUntil(oeuvre -> oeuvre.titre().startsWith("T"))));
}

void prettyPrint(Stream<Oeuvre> oeuvres) {
    AsciiTable at = new AsciiTable();
    at.addRule();
    at.addRow("Année", "Titre", "Perdu 😱?");
    at.addRule();
    oeuvres.forEach(oeuvre -> {
        synchronized (at) {
            at.addRow(oeuvre.anneeParution(), oeuvre.titre(), oeuvre.perdue() ? "Oui" : "Non");
            at.addRule();
        }
    });
    System.out.println(at.render());
}
