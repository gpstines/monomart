package mart.mono.commerce.cart;

import mart.mono.inventory.lib.Product;

import java.util.List;
import java.util.UUID;

public interface CartService {
    List<CartItem> get();

    CartItem add(Product product);

    void remove(UUID cartItemId);

    void removeAll();

    void checkOut();
}
