package br.com.bertol;

import br.com.bertol.ui.CommandLine;

public class Router {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage is 'java -jar  <file with routes>.csv'");
            System.exit(1);
        }

        final var commandLine = new CommandLine(args[0]);
        commandLine.run();
    }
}
