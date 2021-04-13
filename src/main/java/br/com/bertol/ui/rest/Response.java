package br.com.bertol.ui.rest;

public class Response {
    private final String route;

    public Response(final String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
