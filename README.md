# Java Development
Enterprise and Android Application

### Good cloud projects

- [zstack](https://github.com/zstackorg/zstack)
- [Archipel](https://github.com/ArchipelProject/Archipel)
- [Interactive realtime visulization](https://github.com/bokeh/bokeh)
- [rundeck](https://github.com/rundeck/rundeck)


### Maven Deployment

- tomcat

```
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.apache.tomcat.maven</groupId>
          <artifactId>tomcat6-maven-plugin</artifactId>
          <version>2.2</version>
        </plugin>
      </plugins>
    </pluginManagement>  
```
` mvn tomcat:run`

- jetty

```
<plugin>
  <groupId>org.eclipse.jetty</groupId>
  <artifactId>jetty-maven-plugin</artifactId>
</plugin>
```
`mvn jetty:run`
