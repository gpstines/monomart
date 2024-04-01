package mart.mono.inventory.lib;

import java.util.UUID;

public interface IProductService {
    Product getProductById(UUID productId);
    void decrementProductQuantity(UUID productId, int quantity);
}
