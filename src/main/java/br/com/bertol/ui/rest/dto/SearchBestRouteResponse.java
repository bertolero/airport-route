package br.com.bertol.ui.rest.dto;

public class SearchBestRouteResponse {
    private final String route;

    public SearchBestRouteResponse(final String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
