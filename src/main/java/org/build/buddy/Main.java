package org.build.buddy;

import org.build.buddy.commandlinner.CommandLinerInteractor;
import picocli.CommandLine;


public class Main {
    /**
     * Main method to run the command line application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CommandLinerInteractor()).execute(args);
        System.exit(exitCode);
    }
}