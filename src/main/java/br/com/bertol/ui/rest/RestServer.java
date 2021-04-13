package br.com.bertol.ui.rest;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RestServer {

    private final RestServerHandler handler;

    public RestServer(final RestServerHandler handler) {
        this.handler = handler;
    }

    public void startServer() throws IOException {
        final var server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/searchBestRoute", handler::handlerSearchBestRoute);
        server.createContext("/api/addNewConnection", handler::handlerAddNewConnection);
        server.setExecutor(null);
        server.start();
    }
}
