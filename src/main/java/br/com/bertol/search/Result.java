package br.com.bertol.search;

import br.com.bertol.model.Airport;

public class Result {

    private final SearchResult searchResult;

    private final Airport airport;

    public Result(final SearchResult searchResult, final Airport airport) {
        this.searchResult = searchResult;
        this.airport = airport;
    }

    public String get() {
        switch (searchResult) {
            case ORIGIN_NOT_FOUND:
                return "Origin not found";
            case DESTINATION_NOT_FOUND:
                return "Destination not found";
            case ROUTE_NOT_FOUND:
                return "Route not found";
        }

        return airport.getReadableRoute();
    }
}
