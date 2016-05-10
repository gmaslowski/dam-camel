dam-camel
=========

A sample playground on distributed DAM system leveraging all of the current goodies such as:
- [Akka](http://akka.io/)
- [Apache Camel](http://camel.apache.org/)
- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Java](https://community.oracle.com/community/java) and [Scala](http://scala-lang.org/)
- [Gradle](http://gradle.org/)

### Playground architecture
TBD!

### Setting up development infrastructure
```
docker-compose -f docker/dev.yml up
```

### Running all components
```
./gradlew :dam-camel-core:bootRun
./gradlew :dam-camel-tw:bootRun
./gradlew :dam-camel-iw:bootRun
```

### Ingesting an asset
```
curl -X POST http://localhost:8080/dam/asset -d "{'name':'helloAsset', 'contentUrl':'https://farm8.staticflickr.com/7336/11770504415_a3304be7d7_o_d.jpg'}" -H "Content-Type: application/json"
```
