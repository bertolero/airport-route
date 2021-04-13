package br.com.bertol.ui.shell;

import br.com.bertol.service.search.RouteSearcher;

import java.util.Scanner;

public class CommandLine implements Runnable {

    private static final String EXIT_COMMAND = "q";

    private final RouteSearcher routeSearcher;

    public CommandLine(final RouteSearcher routeSearcher) {
        this.routeSearcher = routeSearcher;
    }

    @Override
    public void run() {
        final var scanner = new Scanner(System.in);
        try {
            System.out.println("Type 'q' to quit");
            while (true) {
                System.out.print("please enter the route: ");
                final var input = scanner.nextLine();

                // check exit first
                if (EXIT_COMMAND.equalsIgnoreCase(input)) {
                    System.out.println("Exiting.");
                    System.exit(0);
                }

                final var validInput = getSourceAndDestination(input);

                final var result = routeSearcher.searchBestRoute(validInput[0], validInput[1]);

                System.out.format("best route: %s\n", result.get());
            }
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }

    private static String[] getSourceAndDestination(final String input) {
        if (input == null || input.length() == 0 || !input.contains("-")) {
            throw new RuntimeException("Input strung must be 'ORIGIN-DESTINATION'");
        }
        final var sourceAndDestination = input.split("-");
        if (sourceAndDestination.length != 2) {
            throw new RuntimeException("Input strung must be 'ORIGIN-DESTINATION'");
        }
        return sourceAndDestination;
    }
}
