package com.amankrmj.xwizard;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Main CLI class for XWizard - a file processing wizard
 * Similar to Git, this supports subcommands like:
 * xwizard process [options] [files...]
 * xwizard analyze [options] [files...]
 * xwizard convert [options] [files...]
 */
@Command(name = "xwizard",
        version = "XWizard 1.0",
        mixinStandardHelpOptions = true,
        description = "A powerful file processing wizard built with Picocli.",
        subcommands = {
            ProcessCommand.class,
            AnalyzeCommand.class,
            ConvertCommand.class
        })
public class Main implements Callable<Integer> {

    @Option(names = {"-v", "--verbose"}, description = "Enable verbose output", scope = CommandLine.ScopeType.INHERIT)
    private boolean verbose = false;

    @Override
    public Integer call() throws Exception {
        // If no subcommand is provided, show help
        System.out.println("XWizard - File Processing Wizard");
        System.out.println("Use 'xwizard --help' to see available commands");
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("  process   - Process files with various operations");
        System.out.println("  analyze   - Analyze file contents and structure");
        System.out.println("  convert   - Convert files between different formats");
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    // Getter for verbose flag (can be used by subcommands)
    public boolean isVerbose() {
        return verbose;
    }
}
