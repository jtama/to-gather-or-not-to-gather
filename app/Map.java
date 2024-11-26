///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 23+
//COMPILE_OPTIONS --enable-preview --release 23
//RUNTIME_OPTIONS --enable-preview
//DEPS de.vandermeer:asciitable:0.3.2
//SOURCES org/github/jtama/gatherornot/Map.java
//SOURCES org/github/jtama/gatherornot/Reader.java
//SOURCES org/github/jtama/gatherornot/Oeuvre.java

import de.vandermeer.asciitable.AsciiTable;
import org.github.jtama.gatherornot.Oeuvre;
import org.github.jtama.gatherornot.Reader;

import static org.github.jtama.gatherornot.Map.map;
import static org.github.jtama.gatherornot.Map.mapSneakyThrowing;

public void main() throws IOException {
    prettyPrint(Reader.read().stream().gather(map(Oeuvre::titre)));
    prettyPrint(Reader.read().stream().gather(mapSneakyThrowing(_ -> {
        throw new IOException("BOUM");
    })));
}

void prettyPrint(Stream<String> oeuvres) {
    AsciiTable at = new AsciiTable();
    at.addRule();
    at.addRow("Année", "Titre", "Perdu 😱?");
    at.addRule();
    oeuvres.forEach(oeuvre -> {
        synchronized (at) {
            at.addRow("", oeuvre, "");
            at.addRule();
        }
    });
    System.out.println(at.render());
}
