package br.com.bertol.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouteSearcherTest {

    private final RouteTestUtils routeTestUtils = new RouteTestUtils();

    @Test
    public void shouldNotCalculateRouteForUnknownOrigin() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeSearcher = new RouteSearcher(routes);

        // when
        final var result = routeSearcher.getSearchBesRoute("XXX", "CDG");

        // then
        Assertions.assertEquals("Origin not found", result.get());
    }

    @Test
    public void shouldNotCalculateRouteForUnknownDestination() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeSearcher = new RouteSearcher(routes);

        // when
        final var result = routeSearcher.getSearchBesRoute("POA", "XXX");

        // then
        Assertions.assertEquals("Destination not found", result.get());
    }

    @Test
    public void shouldNotFindRoute() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeSearcher = new RouteSearcher(routes);

        // when
        final var result = routeSearcher.getSearchBesRoute("POA", "FTL");

        // then
        Assertions.assertEquals("Route not found", result.get());
    }

    @Test
    public void shouldCalculateRouteFromPoaToCdg() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeSearcher = new RouteSearcher(routes);

        // when
        final var result = routeSearcher.getSearchBesRoute("GRU", "CDG");

        // then
        Assertions.assertEquals("GRU - BRC - SCL - ORL - CDG > 40", result.get());
    }
}
