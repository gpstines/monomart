package mart.mono.commerce.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mart.mono.commerce.confiig.EventBusConfig;
import mart.mono.inventory.lib.IProductService;
import mart.mono.inventory.lib.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@Slf4j
public class ProductService implements IProductService {
    public static final String PURCHASE_EVENT = "purchaseEvent";

    private final RabbitTemplate rabbitTemplate;
    private final RestClient restClient;

    public ProductService(RabbitTemplate jsonRabbitTemplate, RestClient restClient, Jackson2JsonMessageConverter jsonMessageConverter) {
        this.rabbitTemplate = jsonRabbitTemplate;
        rabbitTemplate.setMessageConverter(jsonMessageConverter);

        this.restClient = restClient;
    }

    @Override
    public Product getProductById(UUID productId) {
        return restClient.get()
            .uri("/api/products/{0}", productId)
            .retrieve()
            .body(Product.class);
    }

    @Override
    public void decrementProductQuantity(UUID productId, int quantity) {
        PurchaseEvent purchaseEvent = PurchaseEvent.builder()
            .productId(productId)
            .quantity(quantity)
            .build();
        log.info("Publishing Event {}", purchaseEvent);
        rabbitTemplate.convertAndSend(EventBusConfig.TOPIC_PRODUCT_PURCHASED, purchaseEvent);
    }
}
