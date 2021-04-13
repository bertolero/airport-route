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

                final var isInputValid = validateInput(input);

                if (isInputValid) {
                    final var validInput = getSourceAndDestination(input);

                    final var result = routeSearcher.searchBestRoute(validInput[0], validInput[1]);

                    System.out.format("best route: %s\n", result.get());
                }
            }
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }

    private boolean validateInput(final String input) {
        if (input == null || input.length() == 0 || !input.contains("-")) {
            System.out.println("Input string must be 'ORIGIN-DESTINATION'");
            return false;
        }
        final var sourceAndDestination = input.split("-");
        if (sourceAndDestination.length != 2) {
            System.out.println("Input string must be 'ORIGIN-DESTINATION'");
            return false;
        }
        return true;
    }

    private String[] getSourceAndDestination(final String input) {
        return input.split("-");
    }
}
