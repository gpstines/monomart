package io.pivotal.pivmart.cart;

import io.pivotal.pivmart.database.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartRepository {
    List<CartItem> findAll();

    CartItem save(CartItem cartItem);

    void remove(CartItem cartItem);

    CartItem find(UUID cartItemId);

    void removeAll();
}
