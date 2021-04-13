package br.com.bertol.ui.rest;

import br.com.bertol.io.AirportInclusion;
import br.com.bertol.search.RouteSearcher;
import br.com.bertol.ui.rest.dto.AddNewConnectionRequest;
import br.com.bertol.ui.rest.dto.SearchBestRouteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class RestServerHandler {

    private final RouteSearcher routeSearcher;

    private final ObjectMapper objectMapper;

    private final AirportInclusion airportInclusion;

    public RestServerHandler(final RouteSearcher routeSearcher, final AirportInclusion airportInclusion) {
        this.routeSearcher = routeSearcher;
        this.objectMapper = new ObjectMapper();
        this.airportInclusion = airportInclusion;
    }

    public void handlerSearchBestRoute(final HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                final var params = splitQuery(exchange.getRequestURI().getRawQuery());
                final var noNameText = "Anonymous";
                final var origin = params.getOrDefault("origin", List.of(noNameText)).stream().findFirst().orElse(noNameText);
                final var destination = params.getOrDefault("destination", List.of(noNameText)).stream().findFirst().orElse(noNameText);
                final var respText = this.routeSearcher.searchBestRoute(origin, destination);
                final var response = new SearchBestRouteResponse(respText.get());
                final var responseString = this.objectMapper.writeValueAsString(response);
                flushStream(exchange, 200, responseString);
            } catch (final Exception e) {
                final var errorMessage = e.getMessage();
                flushStream(exchange, 500, errorMessage);
            }
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    public void handlerAddNewConnection(final HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                final var params = this.objectMapper.readValue(exchange.getRequestBody(), AddNewConnectionRequest.class);
                this.airportInclusion.linkOriginAndDestination(params.getOrigin(), params.getDestination(), params.getDistance());
                final var inputString = this.objectMapper.writeValueAsString(params);
                flushStream(exchange, 200, inputString);
            } catch (final Exception e) {
                final var errorMessage = e.getMessage();
                flushStream(exchange, 500, errorMessage);
            }
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    private void flushStream(final HttpExchange exchange, final int code, final String outputMessage) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, outputMessage.getBytes().length);// 405 Method Not Allowed
        final var output = exchange.getResponseBody();
        output.write(outputMessage.getBytes());
        output.flush();
    }

    private Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));
    }

    private String decode(final String encoded) {
        return encoded == null ? null : URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }
}
