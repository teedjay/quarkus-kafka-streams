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
