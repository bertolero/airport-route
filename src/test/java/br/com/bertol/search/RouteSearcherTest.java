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
        final var result = routeSearcher.getSearchBestRoute("XXX", "CDG");

        // then
        Assertions.assertEquals("Origin not found", result.get());
    }

    @Test
    public void shouldNotCalculateRouteForUnknownDestination() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeSearcher = new RouteSearcher(routes);

        // when
        final var result = routeSearcher.getSearchBestRoute("POA", "XXX");

        // then
        Assertions.assertEquals("Destination not found", result.get());
    }

    @Test
    public void shouldNotFindRoute() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeSearcher = new RouteSearcher(routes);

        // when
        final var result = routeSearcher.getSearchBestRoute("POA", "FTL");

        // then
        Assertions.assertEquals("Route not found", result.get());
    }

    @Test
    public void shouldCalculateRouteFromBrcToCdg() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeSearcher = new RouteSearcher(routes);

        // when
        final var result = routeSearcher.getSearchBestRoute("BrC", "CDG");

        // then
        Assertions.assertEquals("BRC - SCL - ORL - CDG > 30", result.get());
    }
}
