package com.teedjay.kafka;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.stream.LongStream;

@Path("/generate")
@Produces(MediaType.TEXT_PLAIN)
@ApplicationScoped
public class KafkaGenerator {

    @Inject
    @Channel("generator")
    @OnOverflow(OnOverflow.Strategy.NONE)
    Emitter<String> emitter;

    @GET
    public String single() {
        return sendWithAck(1);
    }

    @GET
    @Path("{count}")
    public String sendWithAck(@PathParam("count") long count) {
        LongStream.range(0, count).forEach(i -> emitter.send("[pusher:" + i + "]").toCompletableFuture().join());
        return "Just sent %d messages waiting for downstream ack%n".formatted(count);
    }

    @POST
    @Path("{count}")
    public String fireAndForget(@PathParam("count") long count) {
        LongStream.range(0, count).forEach(i -> emitter.send("[pusher:" + i + "]"));
        return "Just sent %d messages not waiting for any acks%n".formatted(count);
    }

}
