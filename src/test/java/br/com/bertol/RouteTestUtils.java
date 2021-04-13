package br.com.bertol;

import br.com.bertol.model.Airport;
import br.com.bertol.model.Routes;

public class RouteTestUtils {

    public static final String CWB = "CWB";

    public static final String GRU = "GRU";

    public static final String BRC = "BRC";

    public static final String ORL = "ORL";

    public static final String SCL = "SCL";

    public static final String CDG = "CDG";

    public static final String POA = "POA";

    public static final String FTL = "FTL";

    public Routes getRoutesFromAirports() {
        final var cwb = new Airport(CWB);
        final var gru = new Airport(GRU);
        final var brc = new Airport(BRC);
        final var orl = new Airport(ORL);
        final var scl = new Airport(SCL);
        final var cdg = new Airport(CDG);
        final var poa = new Airport(POA);
        final var ftl = new Airport(FTL);

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

        ftl.addNearAirports(orl, 15);

        final var routes = new Routes();

        routes.addAirport(cwb);
        routes.addAirport(gru);
        routes.addAirport(brc);
        routes.addAirport(orl);
        routes.addAirport(scl);
        routes.addAirport(cdg);
        routes.addAirport(poa);
        routes.addAirport(ftl);

        return routes;
    }
}
