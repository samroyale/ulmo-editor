# Map Editor
### A map editor for Ulmo's Adventure implemented in Java

You'll need to have Java + Gradle installed (tested with Java 1.7 + Gradle 1.12)

All commands should be executed from the project root.

To build the project:
```
$ gradle clean build
```

If successful, this will create *ulmo-editor-1.1.jar* under *build/libs*

Before you run it, you'll need to edit the two properties files in *src/test/resources*:
* *maps.properties* <- this needs to point at the *maps* folder in your *ulmo-game* project
* *tiles.properties* <- this needs to point at the *tiles* folder in your *ulmo-game* project

Also tweak *gradle.properties* so it supports your local dev environment.

To run the editor:
```
$ ./mac-run.sh OR $ ./linux-run.sh
```

To create Eclipse artefacts:
```
$ gradle cleanEclipse eclipse
```
