package br.com.bertol.io;

import br.com.bertol.RouteTestUtils;
import br.com.bertol.model.Routes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteRoutesToFileTest {

    private final static String TMP_FILE_NAME = "src/test/resources/input-tmp.csv";

    private final static Path path = Path.of(new File(TMP_FILE_NAME).toURI());

    private final RouteTestUtils routeTestUtils = new RouteTestUtils();

    @BeforeEach
    public void setup() throws IOException {
        final var newConnection = String.format("%s,%s,%d", "GRU","BRC",10);

        final var path = Path.of(new File(TMP_FILE_NAME).toURI());

        Files.writeString(path,
                "GRU,CDG,75\n",
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
        Files.writeString(path,
                "GRU,BRC,10\n",
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
        Files.writeString(path,
                "GRU,ORL,56",
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    @AfterEach
    public void tearDown() throws IOException {
        final var path = Path.of(new File(TMP_FILE_NAME).toURI());

        Files.deleteIfExists(path);
    }

    @Test
    public void shouldPersistNewConnection() throws IOException {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();
        final var writeRoutesToFile = new WriteRoutesToFile(TMP_FILE_NAME, routes);

        // when
        writeRoutesToFile.writeConnectionsToFile("TAO", "BAO", 10);

        // then
        final var lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        assertEquals(4, lines.size());
        assertTrue(lines.get(3).contains("TAO,BAO,10"));
    }

    @Test
    public void shouldUpdateExistentConnection() throws IOException {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();
        final var writeRoutesToFile = new WriteRoutesToFile(TMP_FILE_NAME, routes);

        // when
        writeRoutesToFile.writeConnectionsToFile("GRU", "BRC", 5);

        // then
        final var lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        assertEquals(3, lines.size());
        assertTrue(lines.get(1).contains("GRU,BRC,5"));
        assertTrue(lines.stream().filter(s -> s.equalsIgnoreCase("GRU,BRC,10")).findAny().isEmpty());
    }
}
