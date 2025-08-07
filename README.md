# 🧙‍♂️ XWizard

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![GraalVM](https://img.shields.io/badge/GraalVM-Native-blue.svg)](https://www.graalvm.org/)
[![PicoCLI](https://img.shields.io/badge/PicoCLI-4.x-green.svg)](https://picocli.info/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey.svg)]()

**XWizard** is a powerful command-line tool for Java version management that runs natively on Windows, Linux, and macOS without requiring JVM, JDK, or any external dependencies to be pre-installed.

## 📚 Table of Contents

- [✨ Features](#-features)
- [🚀 Quick Start](#-quick-start)
  - [Installation](#installation)
  - [Basic Usage](#basic-usage)
- [📖 Documentation](#-documentation)
  - [Commands](#commands)
  - [Examples](#examples)
- [🛠️ Development](#️-development)
  - [Prerequisites for Building](#prerequisites-for-building)
  - [Building from Source](#building-from-source)
  - [Project Structure](#project-structure)
  - [Technology Stack](#technology-stack)
- [🤝 Contributing](#-contributing)
  - [How to Contribute](#how-to-contribute)
  - [Development Guidelines](#development-guidelines)
  - [Areas We Need Help With](#areas-we-need-help-with)
- [📋 Roadmap](#-roadmap)
- [🚀 Current Sprint](#-current-sprint)
- [📄 License](#-license)
- [🙏 Acknowledgments](#-acknowledgments)
- [💡 Inspiration](#-inspiration)
- [📞 Support](#-support)

## ✨ Features

- 🔄 **Seamless Java Version Switching** - Switch between different Java versions instantly
- 📦 **Zero Dependencies** - Runs as a native executable without requiring JVM installation
- 🌐 **Cross-Platform Support** - Native binaries for Windows, Linux, and macOS
- ⚡ **Lightning Fast** - Built with GraalVM Native Image for optimal performance
- 🎯 **Simple CLI** - Intuitive command-line interface powered by PicoCLI
- 🔧 **Developer Focused** - Designed to streamline Java development workflows

## 🚀 Quick Start

### Installation

#### Windows
```powershell
# Download and run the installer
.\JavaWizard-Native-Setup-1.0.0.exe
```

#### Linux/macOS
```bash
# Download the binary and make it executable
chmod +x xwizard
sudo mv xwizard /usr/local/bin/
```

### Basic Usage

```bash
# List available Java versions
xwizard java list

# Install a specific Java version
xwizard java install 21

# Set global Java version
xwizard java global 21

# Show current Java version
xwizard java version

# Manage PATH environment
xwizard path show
```

## 📖 Documentation

### Commands

| Command | Description |
|---------|-------------|
| `xwizard java list` | List all available Java versions |
| `xwizard java install <version>` | Install a specific Java version |
| `xwizard java global <version>` | Set global Java version |
| `xwizard java version` | Show current active Java version |
| `xwizard path show` | Display current PATH configuration |
| `xwizard help` | Show help information |

### Examples

```bash
# Install Java 17 and 21
xwizard java install 17
xwizard java install 21

# Switch to Java 17 globally
xwizard java global 17

# Verify the switch
java -version
```

## 🛠️ Development

### Prerequisites for Building

- **JDK 21** or higher
- **GraalVM Native Image** (for native compilation)
- **Gradle 8+**

### Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/xwizard.git
cd xwizard

# Build the JAR
./gradlew build

# Create native executable (requires GraalVM)
./gradlew nativeCompile
```

### Project Structure

```
src/
├── main/java/com/amankrmj/xwizard/
│   ├── Main.java                    # Application entry point
│   ├── commands/
│   │   └── PathCommand.java         # PATH management commands
│   └── java/
│       ├── InstallJavaVersionCommand.java
│       ├── JavaVersionManagerCommand.java
│       ├── NativeCompilerCommand.java
│       └── SetGlobalVersion.java
└── main/resources/META-INF/native-image/
    ├── proxy-config.json            # GraalVM proxy configuration
    ├── reflect-config.json          # GraalVM reflection configuration
    └── resource-config.json         # GraalVM resource configuration
```

### Technology Stack

- **☕ Java 21** - Modern Java with latest features
- **🎛️ PicoCLI** - Powerful command-line interface framework
- **⚡ GraalVM Native Image** - Ahead-of-time compilation for native executables
- **🏗️ Gradle** - Build automation and dependency management

## 🤝 Contributing

We welcome contributions from the community! Whether you're fixing bugs, adding new features, or improving documentation, your help is appreciated.

### How to Contribute

1. **Fork the repository**
   ```bash
   git fork https://github.com/yourusername/xwizard.git
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Make your changes**
   - Follow the existing code style
   - Add tests for new functionality
   - Update documentation as needed

4. **Test your changes**
   ```bash
   ./gradlew test
   ./gradlew nativeCompile  # Test native compilation
   ```

5. **Commit your changes**
   ```bash
   git commit -m "Add amazing feature"
   ```

6. **Push and create a Pull Request**
   ```bash
   git push origin feature/amazing-feature
   ```

### Development Guidelines

- **Code Style**: Follow Java conventions and maintain consistency
- **Testing**: Ensure all new features have appropriate tests
- **Documentation**: Update README and inline documentation
- **Native Compatibility**: Test native compilation on target platforms

### Areas We Need Help With

- 🐧 **Linux Distribution Support** - Package managers integration
- 🍎 **macOS Optimization** - Native Apple Silicon support
- 🧪 **Testing Framework** - Comprehensive test coverage
- 📚 **Documentation** - User guides and tutorials
- 🌐 **Internationalization** - Multi-language support

## 📋 Roadmap

- [ ] **v1.1.0**: Support for additional JVM distributions (Temurin, Corretto, etc.)
- [ ] **v1.2.0**: Project-specific Java version management (.java-version files)
- [ ] **v1.3.0**: Integration with popular build tools (Maven, Gradle)
- [ ] **v2.0.0**: Support for other programming languages (Python, Node.js, Go, Rust)

## 🚀 Current Sprint

We're actively working on the following features and improvements:

### 🌐 Web Interface Development
- Building a visual web interface for interactive Java version management
- Creating user-friendly GUI for non-CLI users
- Developing real-time status dashboards and configuration panels

### 🧪 Core Testing & Validation
- Comprehensive testing of Java version management functionality
- Validating installation, switching, and global version settings
- Performance benchmarking of native executables across platforms

### 📦 Native Build Optimization
- Refining GraalVM native compilation process
- Optimizing binary size and startup performance
- Ensuring consistent behavior across Windows, Linux, and macOS

*Stay tuned for updates as we enhance XWizard's capabilities!*

## 📄 License

This project is licensed under the GNU Affero General Public License v3.0 (GNU AGPLv3) - see the [LICENSE](LICENSE) file for details.

### License Summary

The **GNU AGPLv3** is a strong copyleft license that ensures this software remains free and open source:

- ✅ **You can freely**: Use, study, modify, and distribute this software
- ✅ **You must**: Share the source code of any modifications or derivative works
- ✅ **Network use**: If you run a modified version as a service, you must make the complete source code available
- ✅ **Patent protection**: Contributors grant patent rights to users
- ⚠️ **Same license**: Any larger works that include this code must also use AGPLv3

This license ensures that XWizard and any improvements to it remain free and available to everyone in the community.

## 🙏 Acknowledgments

- **Oracle JDK 21** - For providing the modern Java platform
- **GraalVM** - For enabling native compilation and ahead-of-time optimization
- **PicoCLI** - For the excellent command-line interface framework

## 💡 Inspiration

XWizard was inspired by excellent version management tools in the developer ecosystem:

- **[SDKMAN!](https://sdkman.io/)** - The Software Development Kit Manager for managing parallel versions of multiple SDKs
- **[pyenv](https://github.com/pyenv/pyenv)** - Simple Python version management that pioneered the local/global version concept

These tools demonstrated the power of seamless version switching and inspired us to create a native, dependency-free solution for Java developers.

## 📞 Support

- 🐛 **Bug Reports**: [Create an issue](https://github.com/yourusername/xwizard/issues/new?template=bug_report.md)
- 💡 **Feature Requests**: [Create an issue](https://github.com/yourusername/xwizard/issues/new?template=feature_request.md)
- 💬 **Discussions**: [Join our discussions](https://github.com/yourusername/xwizard/discussions)

---

<div align="center">

**Made with ❤️ by the XWizard**

[Website](https://xwizard.dev) • [Documentation](https://docs.xwizard.dev) • [Twitter](https://twitter.com/xwizard_dev)

</div>
