package br.com.bertol.search;

import br.com.bertol.model.Routes;

public class RouteSearcher {

    private final RouteCalculator routeCalculator;

    private final Routes routes;

    public RouteSearcher(final Routes routes) {
        this.routeCalculator = new RouteCalculator();
        this.routes = routes;
    }

    public Result getSearchBestRoute(final String origin, final String destination) {
        // always start a new search as a new search
        routes.clearRoutesToAllAirports();

        final var optionalOrigin = routes.getAirportByName(origin);

        final var optionalDestination = routes.getAirportByName(destination);

        if (optionalOrigin.isEmpty()) {
            return new Result(SearchResult.ORIGIN_NOT_FOUND, null);
        }
        if (optionalDestination.isEmpty()) {
            return new Result(SearchResult.DESTINATION_NOT_FOUND, null);
        }

        final var originAirport = optionalOrigin.get();

        // pass origin to search algorithm and calculate all routes
        routeCalculator.calculateBestRoutesFromSource(originAirport);

        final var route = routes.getAirports().stream()
                .filter(airport -> airport.getName().equalsIgnoreCase(destination))
                .findAny().get();

        if (!route.getBestRoute().contains(originAirport)) {
            return new Result(SearchResult.ROUTE_NOT_FOUND, null);
        }

        return new Result(SearchResult.ROUTE_FOUND, route);
    }
}
