package br.com.bertol;

import br.com.bertol.input.InputReader;
import br.com.bertol.search.RouteSearcher;
import br.com.bertol.ui.CommandLine;
import br.com.bertol.ui.RestServerHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Router {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage is 'java -jar  <file with routes>.csv'");
            System.exit(1);
        }

        final var inputReader = new InputReader(args[0]);
        final var routes = inputReader.getRoutes();
        final var routeSearcher = new RouteSearcher(routes);

        final var commandLine = new Thread(new CommandLine(routeSearcher));
        commandLine.start();

        final var handler = new RestServerHandler(routeSearcher);
        final var server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/searchBestRoute", handler::handlerSearchBestRoute);
        server.createContext("/api/addNewConnection", handler::handlerAddNewConnection);
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
