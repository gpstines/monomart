package mart.mono.commerce.confiig;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusConfig {
    public static final String TOPIC_PRODUCT_PURCHASED = "product.purchased";

    public final CachingConnectionFactory connectionFactory;

    public EventBusConfig(CachingConnectionFactory cachingConnectionFactory) {
        this.connectionFactory = cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.setExchange("commerce");
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
