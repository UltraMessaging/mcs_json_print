# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build

```sh
cp lbm.sh.example lbm.sh   # once; then edit L= to point to your UM install
./bld.sh
```

`bld.sh` updates markdown TOCs (via `mdtoc.pl` if available), then compiles `JsonPrint.java` against the MCS jars referenced through `lbm.sh`, and packages the result into `JsonPrint.jar`. The `lbm.sh` file is gitignored and must be created locally.

There are no automated tests.

## Architecture

This is a single-class UM MCS plugin. `JsonPrint.java` implements the `UMMonDB` interface from `com.latencybusters.lbm`, which the MCS calls to deliver monitoring protobuf messages from UM library instances and daemons (Store, DRO, SRS). The four `write()` overloads each delegate to `printMsg()`, which uses `com.google.protobuf.util.JsonFormat` to serialize any protobuf `Message` as a compact JSON line written to the configured output file (or stdout if `outFilePath=-`).

The MCS instantiates the plugin via reflection (`class:JsonPrint` in `mcs.xml`), calls `setProperties()` with the contents of `mcs.properties`, then `connect()`. `connect()` opens the output file and registers a JVM shutdown hook to flush/close it. `disconnect()` also flushes and closes.

The plugin is loaded into the MCS JVM alongside the MCS jars; `JsonPrint.jar` must appear on the MCS classpath (the stock `MCS` launch script cannot be used as-is because it hard-codes the classpath).

## Repo management

Claude Code maintains this repository and has permission to edit files, commit changes, and push to the remote as appropriate.

## UM skill

Load the `um-ref` skill when working on UM API usage, configuration, or daemon interactions.
