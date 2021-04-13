package br.com.bertol.service.connection;

import br.com.bertol.RouteTestUtils;
import br.com.bertol.service.connections.AirportInclusion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirportInclusionTest {

    private final RouteTestUtils routeTestUtils = new RouteTestUtils();

    @Test
    public void shouldCreateNewConnection() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();
        final var airportInclusion = new AirportInclusion(routes);

        // when
        airportInclusion.linkOriginAndDestination("BSB", "POA", 10);

        // then
        final var bsbAirport = routes.getAirportByName("BSB").get();
        final var poaAirport = routes.getAirportByName("POA").get();

        assertEquals(9, routes.getAirports().size());
        assertEquals(1, bsbAirport.getAdjacentAirports().size());
        assertEquals(10, bsbAirport.getAdjacentAirports().get(poaAirport));
    }

    @Test
    public void shouldUpdateConnection() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();
        final var airportInclusion = new AirportInclusion(routes);

        assertEquals(8, routes.getAirports().size());
        final var orlAirport = routes.getAirportByName("ORL").get();
        final var ftlAirport = routes.getAirportByName("FTL").get();

        assertEquals(15, ftlAirport.getAdjacentAirports().get(orlAirport));

        // when
        airportInclusion.linkOriginAndDestination("FTL", "ORL", "10");

        // then
        assertEquals(8, routes.getAirports().size());
        assertEquals(10, ftlAirport.getAdjacentAirports().get(orlAirport));
    }
}
