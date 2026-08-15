# Hyperion

> **Project status:** Work in progress (WIP).

## 1. General

Hyperion is a desktop web browser implemented in Java on top of the Java Chromium Embedded Framework (JCEF). The project was developed as a study of how modern browsers are structured, with the aim of building a functional graphical browser shell around an embedded Chromium rendering engine rather than implementing a rendering engine from scratch.

---

## 2. Features

| Feature | Description | Status |
| --- | --- | --- |
| Page rendering | Full web page rendering through the embedded Chromium engine | Implemented |
| Tab management | Opening and closing of multiple browsing tabs | Implemented |
| Navigation | Back, forward, and reload operations | Implemented |
| Bookmark manager | Persistent storage and retrieval of user-selected pages | Implemented |
| History tracker | Chronological record of visited pages | Implemented |

## 3. Technology Stack

| Component | Purpose |
| --- | --- |
| Java (JDK 17 or later) | Application language |
| Swing | Graphical user interface toolkit |
| JCEF (Java Chromium Embedded Framework) | Embedded Chromium rendering engine |
| Apache Maven | Build automation and dependency management |

## 4. System Requirements

- **Operating system:** Windows 10 or later (the installation instructions below are Windows-specific; JCEF also supports Linux and macOS)
- **Java Development Kit:** JDK 17 or later
- **Apache Maven:** version 3.8 or later (required only when building from source)
- **Disk space:** approximately 500 MB, primarily for the JCEF native binaries
- **Network connection:** required at first launch, as remaining dependencies are downloaded on demand

## 5. Installation

Two installation paths are available. Building from source is intended for development and inspection of the code; the prebuilt release is intended for users who only wish to run the application.

### 5.1 Option A — Building from Source

**Step 1: Clone the repository**

```bash
git clone https://github.com/parcivalltd/Hyperion.git
cd Hyperion
```

**Step 2: Install Apache Maven**

If Maven is not already installed:

1. Download the binary archive from the [official Apache Maven website](https://maven.apache.org/download.cgi).
2. Extract the archive to a directory of your choice, for example `C:\Program Files\Apache Maven`.
3. Add the Maven `bin` directory to the system `PATH` environment variable so that Maven can be invoked from any working directory:
   - Right-click the Start button and select **System**.
   - Select **Advanced system settings**.
   - Click **Environment Variables**.
   - Under **System variables**, select the `Path` variable and click **Edit**.
   - Click **New** and add the path to the Maven `bin` directory (e.g. `C:\Program Files\Apache Maven\bin`).
   - Confirm with **OK**.
4. Verify the installation by opening a new terminal and running:

   ```bash
   mvn -version
   ```

**Step 3: Obtain the JCEF bundle**

See [Section 6](#6-the-jcef-dependency).

**Step 4: Build the project**

From the project root directory:

```bash
mvn clean install
```

### 5.2 Option B — Using a Prebuilt Release

1. Open the **Releases** section of this repository and download the latest stable release.
2. Download both the `.jar` file and the accompanying dependency archive.
3. Extract the dependency archive into the same directory as the `.jar` file.
4. Launch the `.jar` file.

On first launch, the application downloads the remaining runtime libraries. Depending on the network connection, this may take several minutes; the browser window opens once the process has completed.

## 6. The JCEF Dependency

JCEF ships with the native Chromium binaries and therefore exceeds 200 MB, which is above the practical size limit for a Git repository. The bundle is consequently hosted externally:

**[Download the JCEF bundle](https://brgrohrbach-my.sharepoint.com/:f:/g/personal/julian_gabriel_brgrohrbach_ac_at/EjBz9_OHVzFAnBWIZEzkplwBrfRKGGiiM_st_b3SbRC6tQ?e=Q3RdzV)**

After downloading, place the bundle folder in the location expected by the build configuration before running `mvn clean install`.

> **Note:** External hosting means this link may expire. If it is no longer reachable, JCEF can be obtained from the [upstream JCEF project](https://github.com/chromiumembedded/java-cef).

## 7. Project Structure

```
Hyperion/
├── src/
│   └── main/
│       ├── java/          # Application source code
│       └── resources/     # Icons, configuration, and static assets
├── pom.xml                # Maven build configuration
└── README.md
```

## 8. Known Limitations

As a work in progress, Hyperion currently has the following constraints:

- Installation and setup have been tested only on Windows.
- The JCEF bundle must be obtained and placed manually; it is not resolved automatically by Maven.
- Advanced browser functionality — extensions, private browsing, synchronisation, and download management — is not implemented.

## 9. Roadmap

- Automated resolution of the JCEF dependency during the build
- Verified builds for Linux and macOS
- Download manager
- Configurable settings and user preferences
- Automated tests for navigation, bookmark, and history components

## 10. License

*(Specify a license here — for example MIT, Apache 2.0, or GPL-3.0 — and add the corresponding `LICENSE` file to the repository.)*

## 11. Author and Acknowledgements

Developed by **Julian Gabriel** ([@parcivalltd](https://github.com/parcivalltd)).

This project builds on the [Chromium Embedded Framework](https://bitbucket.org/chromiumembedded/cef) and its Java binding, [JCEF](https://github.com/chromiumembedded/java-cef).
