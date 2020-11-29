# quarkus-kafka-streams
Testing health, metrics and transactions using Kafka Streams on Quarkus

```
curl http://localhost:8080/generate          <== generate a single message
curl http://localhost:8080/generate/{count}  <== generate lots of messages

curl -X POST http://localhost:8080/generate/{count}  <== generate lots of messages without waiting for downstream acs

http://localhost:8080/health-ui/
http://localhost:8080/swagger-ui/

http://localhost:8080/metrics/
http://localhost:8080/openapi
```

## Prerequisite
You need JDK 15 to compile and run this project and you also need to enable preview mode since record are used.
Right now enabling preview mode for compiling and dev mode works out of the box, not need to do anthing specific.

But for some test and some more advanced build processing you might need to set this ENV variable.
```
export _JAVA_OPTIONS="--enable-preview"
```

## Push to Docker Hub
Configured with JIB, will push directly to docker hub
```
./mvnw clean package -Dmaven.test.skip=true -Dquarkus.container-image.push=true
```

## Override settings with .env file
Place the .env file with ENV variables you want to override in the root folder (together with pom.xml).
When you compile `mvn clean quarkus:dev` this is added to `target` folder automatically.

Note that all ENV settings should be upper cased.
Use underscores to replace any . and - symbols.

A simple .env file can look like the one below,
see the file [env example file](.env-example) for a more detailed example.
```
QUARKUS_KAFKA_STREAMS_BOOTSTRAP_SERVERS = kafka.somewhere.safe:9094
```
