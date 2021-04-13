package br.com.bertol.model;

import br.com.bertol.RouteTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoutesTest {
    private final RouteTestUtils routeTestUtils = new RouteTestUtils();

    @Test
    public void shouldConnectionExist() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        // when
        final var result = routes.connectionExist("CWB", "POA");

        // then
        assertTrue(result);
    }

    @Test
    public void shouldConnectionNotExist() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        // when
        final var result = routes.connectionExist("CWB", "SCL");

        // then
        assertFalse(result);
    }

    @Test
    public void shouldConnectionNotExistDueMissingAirport() {
        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        // when
        final var result = routes.connectionExist("CWB", "SCLW");

        // then
        assertFalse(result);
    }
}
