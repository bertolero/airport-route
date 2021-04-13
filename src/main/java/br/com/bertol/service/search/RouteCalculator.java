package br.com.bertol.service.search;

import br.com.bertol.model.Airport;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class RouteCalculator {

    public void calculateBestRoutesFromSource(final Airport airport) {
        airport.setDistance(0);

        final Set<Airport> distanceFullyCalculatedToAdjacentAirports = new HashSet<>();
        final Set<Airport> distanceNotFullyCalculatedToAdjacentAirports = new HashSet<>();
        distanceNotFullyCalculatedToAdjacentAirports.add(airport);

        while (distanceNotFullyCalculatedToAdjacentAirports.size() != 0) {
            final var currentAirport = getNearestAirport(distanceNotFullyCalculatedToAdjacentAirports);
            distanceNotFullyCalculatedToAdjacentAirports.remove(currentAirport);
            for (final var adjacencyPair : currentAirport.getAdjacentAirports().entrySet()) {
                final var adjacentAirport = adjacencyPair.getKey();
                final var distanceToAdjacentAirport = adjacencyPair.getValue();

                if (!distanceFullyCalculatedToAdjacentAirports.contains(adjacentAirport)) {
                    doCalDistanceToAdjacentAirport(adjacentAirport, distanceToAdjacentAirport, currentAirport);
                    distanceNotFullyCalculatedToAdjacentAirports.add(adjacentAirport);
                }
            }
            distanceFullyCalculatedToAdjacentAirports.add(currentAirport);
        }
    }

    private void doCalDistanceToAdjacentAirport(final Airport destinationAirport, final Integer distanceToNextAirport, final Airport originAirport) {
        final Integer currentDistanceFromOrigin = originAirport.getDistance();
        if (currentDistanceFromOrigin + distanceToNextAirport < destinationAirport.getDistance()) {
            destinationAirport.setDistance(currentDistanceFromOrigin + distanceToNextAirport);
            final var bestRoute = new LinkedList<>(originAirport.getBestRoute());
            bestRoute.add(originAirport);
            destinationAirport.setBestRoute(bestRoute);
        }
    }

    private Airport getNearestAirport(final Set<Airport> distanceNotFullyCalculatedToAdjacentAirports) {
        Airport nearestAirport = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (final var airport : distanceNotFullyCalculatedToAdjacentAirports) {
            final var airportDistance = airport.getDistance();
            if (airportDistance < lowestDistance) {
                lowestDistance = airportDistance;
                nearestAirport = airport;
            }
        }
        return nearestAirport;
    }
}
