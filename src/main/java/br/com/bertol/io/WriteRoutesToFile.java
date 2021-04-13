package br.com.bertol.io;

import br.com.bertol.model.Routes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class WriteRoutesToFile {

    private final Routes routes;

    private final String fileName;

    public WriteRoutesToFile(final String fileName, final Routes routes) {
        this.fileName = fileName;
        this.routes = routes;
    }

    public void writeConnectionsToFile(final String origin, final String destination, final int distance) throws IOException {
        final var connectionExists = routes.connectionExist(origin, destination);

        final var path = Path.of(new File(this.fileName).toURI());

        if (!connectionExists) {
            final var newConnection = String.format("\n%s,%s,%d", origin, destination, distance);

            Files.writeString(path,
                    newConnection,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } else {
            final var originAirport = routes.getAirportByName(origin).get();
            final var destinationAirport = routes.getAirportByName(destination).get();
            final var currentDistance = originAirport.getAdjacentAirports().get(destinationAirport);
            final var currentConnection = String.format("%s,%s,%d", origin, destination, currentDistance);
            final var newConnection = String.format("%s,%s,%d", origin, destination, distance);

            Files.writeString(path,
                    Files.readString(path, StandardCharsets.UTF_8)
                            .replace(currentConnection, newConnection),
                    StandardCharsets.UTF_8);
        }
    }
}
