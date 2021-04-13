package br.com.bertol.ui;

import br.com.bertol.search.RouteSearcher;
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


    public RestServerHandler(RouteSearcher routeSearcher) {
        this.routeSearcher = routeSearcher;
        this.objectMapper = new ObjectMapper();
    }

    public void handlerSearchBestRoute(final HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            final var params = splitQuery(exchange.getRequestURI().getRawQuery());
            final var noNameText = "Anonymous";
            final var origin = params.getOrDefault("origin", List.of(noNameText)).stream().findFirst().orElse(noNameText);
            final var destination = params.getOrDefault("destination", List.of(noNameText)).stream().findFirst().orElse(noNameText);
            final var respText = this.routeSearcher.getSearchBestRoute(origin, destination);
            flushStream(exchange, 200, respText.get());
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    public void handlerAddNewConnection(final HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                final var params = this.objectMapper.readValue(exchange.getRequestBody(), Input.class);
                //final var respText = this.routeSearcher.getSearchBestRoute(origin, destination);
                final var inputString = this.objectMapper.writeValueAsString(params);
                // headers
                exchange.getResponseHeaders().add("Content-Type", "application/json");
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
