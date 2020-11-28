package com.teedjay.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.processor.RecordContext;
import org.apache.kafka.streams.processor.TopicNameExtractor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.time.LocalDateTime;
import java.util.Random;

@ApplicationScoped
public class KafkaStreamProcessor {

    @ConfigProperty(name = "topic.input")
    String TOPIC_INPUT;

    @ConfigProperty(name = "topic.output")
    String TOPIC_OUTPUT;

    @ConfigProperty(name = "topic.dlq")
    String TOPIC_DLQ;

    Random random = new Random();

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        builder
            .stream(TOPIC_INPUT, Consumed.with(Serdes.String(), Serdes.String()))
            .map(this::converter)
            .to(new TopicNameDecider())
            ;
        return builder.build();
    }

    class TopicNameDecider implements TopicNameExtractor<String,String> {
        @Override
        public String extract(String key, String value, RecordContext recordContext) {
            String topic = (random.nextInt(100) < 30) ? TOPIC_DLQ : TOPIC_OUTPUT; // 30% error rate
            //System.out.printf("Decided topic %s for key %s%n", topic, key);
            return topic;
        }
    }

    @Counted
    KeyValue<String, String> converter(String key, String value) {
        String newvalue = new StringBuffer(value).reverse().toString();
        System.out.printf("Mapping '%s' to '%s' for key #%s @ %s%n", value, newvalue, key, LocalDateTime.now().toString());
        return new KeyValue<>(key, newvalue);
    }

}
