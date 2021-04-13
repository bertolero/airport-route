package br.com.bertol.ui.rest.dto;

public class AddNewConnectionRequest {
    private String origin;

    private String destination;

    private int distance;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "AddNewConnectionRequest{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance +
                '}';
    }
}
