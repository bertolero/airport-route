package br.com.bertol.service.search;

import br.com.bertol.RouteTestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static br.com.bertol.RouteTestUtils.BRC;
import static br.com.bertol.RouteTestUtils.CDG;
import static br.com.bertol.RouteTestUtils.CWB;
import static br.com.bertol.RouteTestUtils.ORL;
import static br.com.bertol.RouteTestUtils.POA;
import static br.com.bertol.RouteTestUtils.SCL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteCalculatorTest {

    private final RouteTestUtils routeTestUtils = new RouteTestUtils();

    @Test
    public void shouldCalculateBestPathToAllAirports() {

        // given
        final var routes = routeTestUtils.getRoutesFromAirports();

        final var routeCalculator = new RouteCalculator();

        // when
        routeCalculator.calculateBestRoutesFromSource(routes.getAirportByName(CWB).get());

        // then
        final var shortestPathForAirportBrc = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get());
        final var shortestPathForAirportScl = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get(),
                        routes.getAirportByName(BRC).get());
        final var shortestPathForAirportOrl = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get(),
                        routes.getAirportByName(BRC).get(), routes.getAirportByName(SCL).get());
        final var shortestPathForAirportCdg = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get(),
                        routes.getAirportByName(BRC).get(), routes.getAirportByName(SCL).get(),
                        routes.getAirportByName(ORL).get());

        for (final var airport : routes.getAirports()) {
            switch (airport.getName()) {
                case BRC:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportBrc);
                    assertEquals(14, airport.getDistance());
                    break;
                case ORL:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportOrl);
                    assertEquals(39, airport.getDistance());
                    break;
                case SCL:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportScl);
                    assertEquals(19, airport.getDistance());
                    break;
                case CDG:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportCdg);
                    assertEquals(44, airport.getDistance());
                    break;
            }
        }
    }
}
