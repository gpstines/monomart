package mart.mono.inventory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventBusConfig {
    public static final String Q_PRODUCT_PURCHASED = "inventory.product.purchased";

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Declarables amqpCommerceDeclarables() {
        // Commerce queues and bindings
        var commerceExchange = new TopicExchange("commerce");
        var productPurchasedQueue = new Queue(Q_PRODUCT_PURCHASED);
        var productPurchasedBinding = BindingBuilder.bind(productPurchasedQueue).to(commerceExchange).with("product.purchased");

        return new Declarables(
                commerceExchange,
                productPurchasedQueue,
                productPurchasedBinding);
    }
}
