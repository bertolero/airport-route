package br.com.bertol.io;

import br.com.bertol.model.Airport;
import br.com.bertol.model.Routes;
import br.com.bertol.service.AirportInclusion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadRoutesFromFile {

    private final String fileName;

    private final AirportInclusion airportInclusion;

    public ReadRoutesFromFile(final String fileName, final AirportInclusion airportInclusion) {
        this.fileName = fileName;
        this.airportInclusion = airportInclusion;
    }

    public Routes loadRoutesFromFile() {
        final var routes = new Routes();

        final var path = Path.of(new File(this.fileName).toURI());

        try {
            final var lines = Files.lines(path);

            lines.map(line -> {
                final var splitLine = line.split(",");
                validateInputLine(splitLine);
                return splitLine;
            }).forEachOrdered(piecesOfLine -> {
                airportInclusion.linkOriginAndDestination(piecesOfLine[0], piecesOfLine[1], piecesOfLine[2]);
            });

            return routes;
        } catch (final IOException e) {
            final var exceptionMessage = String.format("Error reading input file %s.", this.fileName);
            throw new RuntimeException(exceptionMessage);
        }
    }

    private Airport createIfNullAirportFromRoutes(final Routes routes, final String airportName) {
        final var optionalAirport = routes.getAirportByName(airportName);

        // add airport to routes
        if (optionalAirport.isPresent()) {
            return optionalAirport.get();
        }

        final var newAirport = new Airport(airportName);
        routes.addAirport(newAirport);
        return newAirport;
    }

    private void validateInputLine(final String[] inputLine) {
        if (inputLine.length != 3) {
            final var exceptionMessage = String.format("Line %s contains wrong number of columns. Must be 3.", String.join(",", inputLine));
            throw new RuntimeException(exceptionMessage);
        }
        try {
            Integer.parseInt(inputLine[2]);
        } catch (final Exception e) {
            final var exceptionMessage = String.format("Line contains wrong value for distance. Must be integer. Current value is %s.", inputLine[2]);
            throw new RuntimeException(exceptionMessage);
        }
    }
}
