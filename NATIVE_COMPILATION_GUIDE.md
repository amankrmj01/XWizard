# GraalVM Native Compilation - Build vs Runtime

## BUILD TIME (Development Machine)
✅ **GraalVM Required** - For compilation only
- Windows: GraalVM + Visual Studio Build Tools
- macOS: GraalVM + Xcode Command Line Tools  
- Linux: GraalVM + gcc/build-essential

## RUNTIME (Target Machines)
❌ **NO GraalVM Required** - Executable is standalone
- Windows: Just run `javawizard.exe`
- macOS: Just run `javawizard` 
- Linux: Just run `javawizard`

## What Users Get
📦 **Completely Self-Contained Executables**
- No JVM installation needed
- No GraalVM installation needed
- No Java runtime dependencies
- Just double-click and run!

## Cross-Platform Build Strategy

### Option 1: Build on Each Platform
```bash
# On Windows (creates javawizard.exe)
gradlew nativeCompile

# On macOS (creates javawizard for macOS)
./gradlew nativeCompile

# On Linux (creates javawizard for Linux)
./gradlew nativeCompile
```

### Option 2: Use CI/CD (Recommended)
- GitHub Actions with multiple runners
- Build for Windows, macOS, Linux automatically
- Upload artifacts for each platform

### Option 3: Cross-Compilation (Limited)
- GraalVM has limited cross-compilation support
- Mainly works for similar architectures
- Not recommended for production

## Your Current Setup
✅ Windows: `javawizard.exe` (standalone, no dependencies)
⭐ Next: Build on macOS for `.app` bundle
⭐ Next: Build on Linux for native executable

## End User Experience
1. Download `javawizard.exe` (Windows)
2. Double-click to run
3. No installation of Java/GraalVM needed!
