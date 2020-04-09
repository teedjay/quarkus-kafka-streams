# quarkus-kafka-streams
Testing health, metrics and transactions using Kafka Streams on Quarkus

- [ ] http://localhost:8080/swagger-ui
- [ ] http://localhost:8080/metrics
- [ ] http://localhost:8080/metrics/application
- [ ] http://localhost:8080/health
- [ ] http://localhost:8080/health/ready
- [ ] http://localhost:8080/health/live

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
