# Map Editor
### A map editor for Ulmo's Adventure written in Java

You'll need to have Java + Gradle installed (I'm using Java 1.7 + Gradle 1.12)

From the project root:
```
$ gradle clean build
```

If successful, that will create *editor-1.0.jar* under *build/libs*

Before you run it, you'll need to edit the two properties files in *src/test/resources*:
* *maps.properties* <- this needs to point to your maps folder in the game project
* *tiles.properties* <- this needs to point to your tiles folder in the game project

To run the editor:
```
$ ./mac-run.sh OR $ ./linux-run.sh
```


To import into Eclipse:
```
$ gradle cleanEclipse eclipse
```
