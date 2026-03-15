package org.mpravia.message.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.mpravia.message.producer.dto.OrderCreatedEvent;

@ApplicationScoped
public class ProductProducer {

    @Inject
    @Channel("orders")
    Emitter<OrderCreatedEvent> emitter;

    public void sendOrderEvent(OrderCreatedEvent event) {
        emitter.send(event);
    }
}
