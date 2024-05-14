package mart.mono.commerce.cart;

import brave.Tracer;
import io.micrometer.tracing.annotation.ContinueSpan;
import lombok.RequiredArgsConstructor;
import mart.mono.inventory.lib.IProductService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CartItemRetriever {

    private final IProductService productService;

    @Async
    @ContinueSpan
    public CompletableFuture<CartItem> toCartItem(CartItemEntity cartItemEntity) {
        return CompletableFuture.completedFuture(CartItem.builder()
            .id(cartItemEntity.getId())
            .quantity(cartItemEntity.getQuantity())
            .product(productService.getProductById(cartItemEntity.getProduct().getId()))
            .build());
    }
}
