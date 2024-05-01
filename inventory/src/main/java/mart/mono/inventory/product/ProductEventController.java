package mart.mono.inventory.product;

import mart.mono.inventory.config.EventBusConfig;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Controller
public class ProductEventController {
    private final ProductService productService;

    public ProductEventController(ProductService productService) {
        this.productService = productService;
    }

//    @RabbitListener(bindings = @QueueBinding(
//            exchange = @Exchange(name="commerce"),
//            value = @Queue(name="inventory.product.purchased", durable = "true"),
//            key = "product.purchased"
//    ))
    @RabbitListener(queues=EventBusConfig.Q_PRODUCT_PURCHASED)
    public void purchaseEvent(PurchaseEvent event) {
        productService.decrementProductQuantity(event.productId(),event.quantity());
    }
}
