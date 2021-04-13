package br.com.bertol.service.connections;

import br.com.bertol.io.WriteRoutesToFile;
import br.com.bertol.ui.rest.dto.AddNewConnectionRequest;

import java.io.IOException;

public class RefreshConnections {

    private final AirportInclusion airportInclusion;

    private final WriteRoutesToFile writeRoutesToFile;

    public RefreshConnections(final AirportInclusion airportInclusion, final WriteRoutesToFile writeRoutesToFile) {
        this.airportInclusion = airportInclusion;
        this.writeRoutesToFile = writeRoutesToFile;
    }

    public void doRefreshConnections(final AddNewConnectionRequest addNewConnectionRequest) throws IOException {
        this.writeRoutesToFile
                .writeConnectionsToFile(addNewConnectionRequest.getOrigin(), addNewConnectionRequest.getDestination(), addNewConnectionRequest.getDistance());
        this.airportInclusion
                .linkOriginAndDestination(addNewConnectionRequest.getOrigin(), addNewConnectionRequest.getDestination(), addNewConnectionRequest.getDistance());
    }
}
