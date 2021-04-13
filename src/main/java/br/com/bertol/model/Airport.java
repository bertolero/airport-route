package br.com.bertol.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Airport {
    private final String name;

    private List<Airport> bestRoute;

    private final Map<Airport, Integer> adjacentAirports;

    private int distance = Integer.MAX_VALUE;

    public Airport(String name) {
        this.name = name;
        this.bestRoute = new LinkedList<>();
        this.adjacentAirports = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addNearAirports(final Airport airport, final int distance) {
        this.adjacentAirports.put(airport, distance);
    }

    public List<Airport> getBestRoute() {
        return bestRoute;
    }

    public void setBestRoute(final List<Airport> bestRoute) {
        this.bestRoute = bestRoute;
    }

    public Map<Airport, Integer> getAdjacentAirports() {
        return adjacentAirports;
    }

    public void setDistance(final int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return name.equals(airport.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", bestRoute=" + bestRoute.stream().map(Airport::getName).collect(Collectors.joining("->")) +
                ", adjacentAirports=" + adjacentAirports +
                '}';
    }

    public String getReadableRoute() {
        final String bestRouteAsString = bestRoute.stream()
                .map(Airport::getName)
                .collect(Collectors.joining(" - ", "", String.format(" - %s", getName())));

        return String.format("%s > %d", bestRouteAsString, getDistance());
    }
}
