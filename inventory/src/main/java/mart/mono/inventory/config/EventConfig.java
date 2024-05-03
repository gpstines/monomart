package mart.mono.inventory.config;

import mart.mono.inventory.product.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConfig {
    @Bean
    public Consumer<PurchaseEvent> purchaseEvent(ProductService service) {
        return service::processPurchaseEvent;
    }
}
