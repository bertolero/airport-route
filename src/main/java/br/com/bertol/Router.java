package br.com.bertol;

import br.com.bertol.service.AirportInclusion;
import br.com.bertol.io.ReadRoutesFromFile;
import br.com.bertol.io.WriteRoutesToFile;
import br.com.bertol.model.Routes;
import br.com.bertol.search.RouteSearcher;
import br.com.bertol.ui.shell.CommandLine;
import br.com.bertol.ui.rest.RestServer;
import br.com.bertol.ui.rest.RestServerHandler;

import java.io.IOException;

public class Router {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage is 'java -jar  <file with routes>.csv'");
            System.exit(1);
        }

        final var routes = new Routes();
        final var airportInclusion = new AirportInclusion(routes);
        final var routeSearcher = new RouteSearcher(routes);
        final var inputReader = new ReadRoutesFromFile(args[0], airportInclusion);
        final var writerRoutesToFile = new WriteRoutesToFile(args[0], routes);
        final var handler = new RestServerHandler(routeSearcher, airportInclusion, writerRoutesToFile);

        // load airports
        inputReader.loadRoutesFromFile();

        final var commandLine = new Thread(new CommandLine(routeSearcher));
        commandLine.start();

        final var restServer = new RestServer(handler);
        restServer.startServer();
    }
}
