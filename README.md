# gradle-buildinfo-plugin

![](https://img.shields.io/badge/status-beta-lightgray.svg)

A Gradle plugin to generate `.buildinfo` files for [reproducible builds](https://reproducible-builds.org/docs/jvm/).

## Usage

Include the plugin in your `build.gradle`

```groovy
plugins {
  id 'uk.co.probablyfine.gradle.build-info' version '0.1.3'
}
```

This plugin exposes a new task, `generateBuildInfo`, which will create a `.buildinfo` file in the root-directory of the project.

```properties
buildinfo.version=1.0-SNAPSHOT

name=example-project
group-id=com.example
artifact-id=example-app
version=1.2.1

java.version=11.0.2
java.vendor=Oracle Corporation
os.name=Linux

outputs.0.filename=example-app-1.2.1.jar
outputs.0.length=5555
outputs.0.checksums.md5=...
outputs.0.checksums.sha1=...
outputs.0.checksums.sha256=...
outputs.0.checksum.sha512=...
```