package br.com.bertol.input;

import br.com.bertol.model.Airport;
import br.com.bertol.model.Routes;

public class AirportInclusion {

    private final Routes routes;

    public AirportInclusion(Routes routes) {
        this.routes = routes;
    }

    public void linkOriginAndDestination(final String origin, final String destination, final int distance) {
        final var originAirport = createIfNullAirportFromRoutes(routes, origin);
        final var destinationAirport = createIfNullAirportFromRoutes(routes, destination);

        // add
        originAirport.addNearAirports(destinationAirport, distance);
    }

    public void linkOriginAndDestination(final String origin, final String destination, final String distance) {
        linkOriginAndDestination(origin, destination, Integer.parseInt(distance));
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
}
