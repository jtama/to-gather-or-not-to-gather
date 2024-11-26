package org.github.jtama.gatherornot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public final class Reader {

    public static List<Oeuvre> read() throws IOException {
        return Files.readAllLines(Path.of("oeuvres.txt"))
                .stream()
                .map(line -> line.split(";"))
                .map(values -> new Oeuvre(values[1], Integer.valueOf(values[0]), Boolean.parseBoolean(values[2])))
                .sorted(Comparator.comparing(Oeuvre::anneeParution))
                .toList();
    }
}
