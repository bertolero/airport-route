package br.com.bertol;

import br.com.bertol.input.AirportInclusion;
import br.com.bertol.input.InputRead;
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

        final var inputReader = new InputRead(args[0]);
        final var routes = inputReader.getRoutes();
        final var routeSearcher = new RouteSearcher(routes);
        final var airportInclusion = new AirportInclusion(routes);
        final var handler = new RestServerHandler(routeSearcher, airportInclusion);

        final var commandLine = new Thread(new CommandLine(routeSearcher));
        commandLine.start();

        final var restServer = new RestServer(handler);
        restServer.startServer();
    }
}
