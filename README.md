# Project Setup

This project is forked from the official template with a few changes:

- Core official libraries for coroutines, serialization, and networking are added.
- Ktor server is replaced with a Vert.x server.
- Basic abstractions for networking are added.

## Prerequisites

- Use Android Studio and install the Kotlin Multiplatform Mobile plugin.

## Running the Server

To run the server, execute the following command:

```bash
./gradlew :vertxServer:runShadow
```

```bash
adb reverse tcp:8888 tcp:8888
```
