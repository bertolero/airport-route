package br.com.bertol.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Routes {
    private final Set<Airport> airports;

    public Routes() {
        this.airports = new HashSet<>();
    }

    public void addAirport(final Airport airport) {
        this.airports.add(airport);
    }

    public Set<Airport> getAirports() {
        return airports;
    }

    public Optional<Airport> getAirportByName(final String airportName) {
        return airports.stream()
                .filter(airport -> airport.getName().equalsIgnoreCase(airportName))
                .findAny();
    }

    public void clearRoutesToAllAirports() {
        airports.forEach(airport -> {
            airport.getBestRoute().clear();
            airport.setDistance(Integer.MAX_VALUE);
        });
    }

    @Override
    public String toString() {
        return "Routes{" +
                "airports=" + airports +
                '}';
    }
}
