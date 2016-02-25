The command to create new maven project:

```
mvn archetype:generate
-DgroupId=com.wonderfan -DartifactId=test 
-DarchetypeArtifactId=maven-archetype-quickstart 
-DinteractiveMode=false
```

```
mvn archetype:create 
  -DgroupId=[your project's group id]
  -DartifactId=[your project's artifact id]
  -DarchetypeArtifactId=maven-archetype-quickstart
```

make the project to eclipse

```
mvn  ecllipse:eclipse
```
