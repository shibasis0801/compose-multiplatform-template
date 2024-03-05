# Project Setup

This project is forked from the official template with a few changes:

- Core official libraries for coroutines, serialization, and networking are added.
- Ktor server is replaced with a Vert.x server.
- Basic abstractions for networking are added.

## Prerequisites

- Use Android Studio and install the Kotlin Multiplatform Mobile plugin.

## Running the Server

Run the server using Android Studio's run configuration for Main.kt, or using the following command

```bash
./gradlew :vertxServer:serve
```

```bash
adb reverse tcp:8888 tcp:8888
```
