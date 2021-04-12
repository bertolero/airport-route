package br.com.bertol.search;

import br.com.bertol.model.Airport;
import br.com.bertol.model.Routes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteCalculatorTest {

    private static final String CWB = "CWB";

    private static final String GRU = "GRU";

    private static final String BRC = "BRC";

    private static final String ORL = "ORL";

    private static final String SCL = "SCL";

    private static final String CDG = "CDG";

    private static final String POA = "POA";

    @Test
    public void shouldCalculateBestPathToAllAirports() {

        // given
        final var routes = getRoutesFromAirports();

        final var routeCalculator = new RouteCalculator();

        // when
        final var bestRoutes = routeCalculator
                .calculateBestRoutesFromSource(routes.getAirportByName(CWB).get());

        // then
        final var shortestPathForAirportBrc = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get());
        final var shortestPathForAirportScl = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get(), routes.getAirportByName(BRC).get());
        final var shortestPathForAirportOrl = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get(), routes.getAirportByName(BRC).get(), routes.getAirportByName(SCL).get());
        final var shortestPathForAirportCdg = Arrays
                .asList(routes.getAirportByName(CWB).get(), routes.getAirportByName(POA).get(), routes.getAirportByName(BRC).get(), routes.getAirportByName(SCL).get(), routes.getAirportByName(ORL).get());

        for (final var airport : bestRoutes) {
            switch (airport.getName()) {
                case BRC:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportBrc);
                    break;
                case ORL:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportOrl);
                    break;
                case SCL:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportScl);
                    break;
                case CDG:
                    assertEquals(airport.getBestRoute(), shortestPathForAirportCdg);
                    break;
            }
        }
    }

    private Routes getRoutesFromAirports() {
        final var cwb = new Airport(CWB);
        final var gru = new Airport(GRU);
        final var brc = new Airport(BRC);
        final var orl = new Airport(ORL);
        final var scl = new Airport(SCL);
        final var cdg = new Airport(CDG);
        final var poa = new Airport(POA);

        cwb.addNearAirports(gru, 5);
        cwb.addNearAirports(poa, 5);

        gru.addNearAirports(brc, 10);
        gru.addNearAirports(cdg, 75);
        gru.addNearAirports(scl, 20);
        gru.addNearAirports(orl, 56);

        brc.addNearAirports(scl, 5);

        orl.addNearAirports(cdg, 5);

        scl.addNearAirports(orl, 20);

        poa.addNearAirports(brc, 9);

        final var routes = new Routes();

        routes.addAirport(cwb);
        routes.addAirport(gru);
        routes.addAirport(brc);
        routes.addAirport(orl);
        routes.addAirport(scl);
        routes.addAirport(cdg);
        routes.addAirport(poa);

        return routes;
    }
}
