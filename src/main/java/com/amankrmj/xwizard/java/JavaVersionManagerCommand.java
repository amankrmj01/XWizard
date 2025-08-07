package com.amankrmj.xwizard.java;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(name = "java",
         description = "Manage Java versions and installations",
         subcommands = {
             JavaVersionManagerCommand.ListCommand.class,
             JavaVersionManagerCommand.InstallCommand.class,
             JavaVersionManagerCommand.UseCommand.class,
             JavaVersionManagerCommand.CurrentCommand.class,
             JavaVersionManagerCommand.WhichCommand.class
         })
public class JavaVersionManagerCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("Java Version Manager - Use 'java --help' for options:");
        System.out.println("  list     - List installed Java versions");
        System.out.println("  install  - Install a specific Java version");
        System.out.println("  use      - Switch to a specific Java version");
        System.out.println("  current  - Show current active Java version");
        System.out.println("  which    - Show path to current Java installation");
        return 0;
    }

    @Command(name = "list", description = "List all installed Java versions")
    static class ListCommand implements Callable<Integer> {

        @Option(names = {"-a", "--all"}, description = "Show all detected Java installations")
        private boolean showAll = false;

        @Override
        public Integer call() throws Exception {
            try {
                if (showAll) {
                    System.out.println("=== All Detected Java Installations ===");
                    findAllJavaInstallations();
                } else {
                    System.out.println("=== Managed Java Versions ===");
                    listManagedVersions();
                }
                return 0;
            } catch (Exception e) {
                System.err.println("Error listing Java versions: " + e.getMessage());
                return 1;
            }
        }

        private void listManagedVersions() throws IOException {
            Path javaVersionsDir = getJavaVersionsDirectory();
            if (!Files.exists(javaVersionsDir)) {
                System.out.println("No managed Java versions found.");
                System.out.println("Use 'javawizard java install <version>' to install Java versions.");
                return;
            }

            String currentVersion = getCurrentJavaVersion();

            Files.list(javaVersionsDir)
                .filter(Files::isDirectory)
                .map(path -> path.getFileName().toString())
                .sorted()
                .forEach(version -> {
                    String marker = version.equals(currentVersion) ? " (current)" : "";
                    System.out.println("  " + version + marker);
                });
        }

        private void findAllJavaInstallations() {
            // Common Java installation paths
            String[] commonPaths = {
                "C:\\Program Files\\Java",
                "C:\\Program Files (x86)\\Java",
                "C:\\Program Files\\Eclipse Adoptium",
                "C:\\Program Files\\Microsoft",
                System.getProperty("user.home") + "\\.jdks"
            };

            System.out.println("Scanning common installation directories...");

            for (String basePath : commonPaths) {
                File baseDir = new File(basePath);
                if (baseDir.exists() && baseDir.isDirectory()) {
                    System.out.println("\n" + basePath + ":");
                    File[] javaInstalls = baseDir.listFiles(File::isDirectory);
                    if (javaInstalls != null) {
                        for (File install : javaInstalls) {
                            String version = detectJavaVersion(install);
                            System.out.println("  " + install.getName() + " " + version);
                        }
                    }
                }
            }

            // Also check JAVA_HOME
            String javaHome = System.getenv("JAVA_HOME");
            if (javaHome != null) {
                System.out.println("\nJAVA_HOME: " + javaHome);
                System.out.println("  Version: " + detectJavaVersion(new File(javaHome)));
            }
        }

        private String detectJavaVersion(File javaHome) {
            try {
                File javaBin = new File(javaHome, "bin/java.exe");
                if (!javaBin.exists()) {
                    javaBin = new File(javaHome, "bin/java");
                }

                if (javaBin.exists()) {
                    ProcessBuilder pb = new ProcessBuilder(javaBin.getAbsolutePath(), "-version");
                    Process process = pb.start();
                    String output = new String(process.getErrorStream().readAllBytes());
                    process.waitFor();

                    // Parse version from output
                    String[] lines = output.split("\n");
                    if (lines.length > 0) {
                        return lines[0].replaceAll(".*\"(.*)\".*", "($1)");
                    }
                }
                return "(unknown)";
            } catch (Exception e) {
                return "(error: " + e.getMessage() + ")";
            }
        }
    }

    @Command(name = "install", description = "Install a specific Java version")
    static class InstallCommand implements Callable<Integer> {

        @Parameters(index = "0", description = "Java version to install (e.g., 17, 21, 11-graalvm)")
        private String version;

        @Option(names = {"-f", "--force"}, description = "Force reinstall if already exists")
        private boolean force = false;

        @Override
        public Integer call() throws Exception {
            System.out.println("Installing Java " + version + "...");

            if (version.contains("graalvm")) {
                return installGraalVM();
            } else {
                return installOpenJDK();
            }
        }

        private Integer installGraalVM() {
            System.out.println("GraalVM Installation Guide:");
            System.out.println("1. Download GraalVM from: https://www.graalvm.org/downloads/");
            System.out.println("2. Choose the appropriate version for your platform:");
            System.out.println("   - Windows: graalvm-community-jdk-<version>_windows-x64_bin.zip");
            System.out.println("   - macOS: graalvm-community-jdk-<version>_macos-x64_bin.tar.gz");
            System.out.println("   - Linux: graalvm-community-jdk-<version>_linux-x64_bin.tar.gz");
            System.out.println("3. Extract to: " + getJavaVersionsDirectory().resolve("graalvm-" + version.replace("-graalvm", "")));
            System.out.println("4. Run: javawizard java use graalvm-" + version.replace("-graalvm", ""));
            return 0;
        }

        private Integer installOpenJDK() {
            System.out.println("OpenJDK Installation Guide:");
            System.out.println("1. Download from Eclipse Adoptium: https://adoptium.net/");
            System.out.println("2. Choose version " + version + " for your platform");
            System.out.println("3. Extract to: " + getJavaVersionsDirectory().resolve("jdk-" + version));
            System.out.println("4. Run: javawizard java use jdk-" + version);
            System.out.println();
            System.out.println("Alternative - Use package managers:");
            System.out.println("Windows (winget): winget install Microsoft.OpenJDK." + version);
            System.out.println("macOS (brew): brew install openjdk@" + version);
            System.out.println("Linux (apt): sudo apt install openjdk-" + version + "-jdk");
            return 0;
        }
    }

    @Command(name = "use", description = "Switch to a specific Java version")
    static class UseCommand implements Callable<Integer> {

        @Parameters(index = "0", description = "Java version to use")
        private String version;

        @Override
        public Integer call() throws Exception {
            try {
                Path javaVersionsDir = getJavaVersionsDirectory();
                Path versionPath = javaVersionsDir.resolve(version);

                if (!Files.exists(versionPath)) {
                    System.err.println("Java version not found: " + version);
                    System.err.println("Available versions:");
                    if (Files.exists(javaVersionsDir)) {
                        Files.list(javaVersionsDir)
                            .filter(Files::isDirectory)
                            .map(path -> path.getFileName().toString())
                            .forEach(v -> System.err.println("  " + v));
                    }
                    return 1;
                }

                // Set JAVA_HOME
                setJavaHome(versionPath.toString());

                // Update PATH
                updatePathForJava(versionPath.resolve("bin").toString());

                System.out.println("Switched to Java version: " + version);
                System.out.println("JAVA_HOME: " + versionPath);
                System.out.println("Note: Restart your terminal to see the changes.");

                return 0;
            } catch (Exception e) {
                System.err.println("Error switching Java version: " + e.getMessage());
                return 1;
            }
        }
    }

    @Command(name = "current", description = "Show current active Java version")
    static class CurrentCommand implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            try {
                String javaHome = System.getenv("JAVA_HOME");
                if (javaHome == null) {
                    System.out.println("JAVA_HOME not set");
                } else {
                    System.out.println("JAVA_HOME: " + javaHome);
                }

                // Try to get version from java command
                ProcessBuilder pb = new ProcessBuilder("java", "-version");
                Process process = pb.start();
                String output = new String(process.getErrorStream().readAllBytes());
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    System.out.println("Active Java version:");
                    System.out.println(output.trim());
                } else {
                    System.out.println("No Java found in PATH");
                }

                return 0;
            } catch (Exception e) {
                System.err.println("Error getting current Java version: " + e.getMessage());
                return 1;
            }
        }
    }

    @Command(name = "which", description = "Show path to current Java installation")
    static class WhichCommand implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            try {
                ProcessBuilder pb = new ProcessBuilder("where", "java");
                Process process = pb.start();
                String output = new String(process.getInputStream().readAllBytes());
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    System.out.println("Java executable locations:");
                    System.out.println(output.trim());
                } else {
                    System.out.println("Java not found in PATH");
                }

                return 0;
            } catch (Exception e) {
                System.err.println("Error finding Java: " + e.getMessage());
                return 1;
            }
        }
    }

    // Helper methods
    private static Path getJavaVersionsDirectory() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, ".javawizard", "java-versions");
    }

    private static String getCurrentJavaVersion() {
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome == null) return null;

        Path javaVersionsDir = getJavaVersionsDirectory();
        Path javaHomePath = Paths.get(javaHome);

        if (javaHomePath.startsWith(javaVersionsDir)) {
            return javaVersionsDir.relativize(javaHomePath).toString();
        }

        return null;
    }

    private static void setJavaHome(String javaHome) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("reg", "add", "HKCU\\Environment",
            "/v", "JAVA_HOME", "/t", "REG_SZ", "/d", javaHome, "/f");
        Process process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String error = new String(process.getErrorStream().readAllBytes());
            throw new RuntimeException("Failed to set JAVA_HOME: " + error);
        }
    }

    private static void updatePathForJava(String javaBinPath) throws IOException, InterruptedException {
        // Get current user PATH
        ProcessBuilder pb = new ProcessBuilder("reg", "query", "HKCU\\Environment", "/v", "Path");
        Process process = pb.start();
        String output = new String(process.getInputStream().readAllBytes());
        process.waitFor();

        String currentPath = "";
        String[] lines = output.split("\n");
        for (String line : lines) {
            if (line.trim().startsWith("Path")) {
                String[] parts = line.trim().split("\\s+", 3);
                if (parts.length >= 3) {
                    currentPath = parts[2].trim();
                    break;
                }
            }
        }

        // Remove any existing Java paths and add new one at the beginning
        String[] pathParts = currentPath.split(";");
        String newPath = javaBinPath + ";" + Arrays.stream(pathParts)
            .filter(part -> !part.toLowerCase().contains("java") || part.trim().isEmpty())
            .collect(Collectors.joining(";"));

        // Set new PATH
        pb = new ProcessBuilder("reg", "add", "HKCU\\Environment",
            "/v", "Path", "/t", "REG_EXPAND_SZ", "/d", newPath, "/f");
        process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String error = new String(process.getErrorStream().readAllBytes());
            throw new RuntimeException("Failed to update PATH: " + error);
        }
    }
}
