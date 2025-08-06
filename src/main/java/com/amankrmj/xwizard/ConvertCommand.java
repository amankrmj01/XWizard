package com.amankrmj.xwizard;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Convert subcommand - handles file format conversion operations
 * Usage: xwizard convert [options] [files...]
 */
@Command(name = "convert",
         description = "Convert files between different formats")
public class ConvertCommand implements Callable<Integer> {

    @ParentCommand
    private Main parent;

    @Option(names = {"-t", "--to"}, description = "Target format (json, xml, csv, txt)", required = true)
    private String targetFormat;

    @Option(names = {"-o", "--output"}, description = "Output directory (default: current directory)")
    private String outputPath = ".";

    @Option(names = {"--overwrite"}, description = "Overwrite existing files")
    private boolean overwrite = false;

    @Option(names = {"--preserve-structure"}, description = "Preserve original file structure")
    private boolean preserveStructure = true;

    @Parameters(description = "Files to convert")
    private List<File> files;

    @Override
    public Integer call() throws Exception {
        if (parent.isVerbose()) {
            System.out.println("Converting files to: " + targetFormat.toUpperCase());
            System.out.println("Output path: " + outputPath);
            System.out.println("Overwrite existing: " + overwrite);
        }

        if (files == null || files.isEmpty()) {
            System.err.println("No files specified for conversion");
            return 1;
        }

        // Validate target format
        if (!isValidFormat(targetFormat)) {
            System.err.println("Invalid target format: " + targetFormat);
            System.err.println("Supported formats: json, xml, csv, txt");
            return 1;
        }

        for (File file : files) {
            if (!file.exists()) {
                System.err.println("File not found: " + file.getPath());
                continue;
            }

            String outputFileName = getOutputFileName(file, targetFormat);
            File outputFile = new File(outputPath, outputFileName);

            if (outputFile.exists() && !overwrite) {
                System.err.println("Output file exists (use --overwrite): " + outputFileName);
                continue;
            }

            if (parent.isVerbose()) {
                System.out.println("Converting: " + file.getName() + " → " + outputFileName);
            }

            // Simulate conversion
            System.out.println("✓ Converted " + file.getName() + " to " + targetFormat.toUpperCase());
        }

        System.out.println("Conversion completed!");
        return 0;
    }

    private boolean isValidFormat(String format) {
        return format != null &&
               (format.equalsIgnoreCase("json") ||
                format.equalsIgnoreCase("xml") ||
                format.equalsIgnoreCase("csv") ||
                format.equalsIgnoreCase("txt"));
    }

    private String getOutputFileName(File inputFile, String targetFormat) {
        String baseName = inputFile.getName();
        int lastDot = baseName.lastIndexOf('.');
        if (lastDot > 0) {
            baseName = baseName.substring(0, lastDot);
        }
        return baseName + "." + targetFormat.toLowerCase();
    }
}
