package br.com.bertol;

import br.com.bertol.input.InputReader;
import br.com.bertol.search.RouteCalculator;
import br.com.bertol.search.RouteSearcher;

import java.util.Scanner;

public class Router {

    public static final String EXIT_COMMAND = "q";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage is 'java -jar  <file with routes>.csv'");
            System.exit(1);
        }
        final var scanner = new Scanner(System.in);
        try {
            final var inputReader = new InputReader(args[0]);
            final var routes = inputReader.getRoutes();
            final var routeSearcher = new RouteSearcher(routes);
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

                final var result = routeSearcher.getSearchBesRoute(validInput[0], validInput[1]);

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
