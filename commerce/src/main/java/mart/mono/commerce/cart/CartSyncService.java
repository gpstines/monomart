package mart.mono.commerce.cart;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.product.ProductEntity;
import mart.mono.commerce.purchase.PurchasesService;
import mart.mono.inventory.lib.IProductService;
import mart.mono.inventory.lib.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Profile("!async")
public class CartSyncService implements CartService {

    private final CartRepository cartRepository;
    private final PurchasesService purchasesService;
    private final IProductService productService;

    @Override
    public List<CartItem> get() {
        return cartRepository.findAll().stream()
            .map(this::toCartItem)
            .toList();
    }

    private CartItem toCartItem(CartItemEntity cartItemEntity) {
        return CartItem.builder()
            .id(cartItemEntity.getId())
            .quantity(cartItemEntity.getQuantity())
            .product(productService.getProductById(cartItemEntity.getProduct().getId()))
            .build();
    }

    @Override
    public CartItem add(Product product) {
        ProductEntity cartProduct = new ProductEntity(product.getId(), product.getName(), product.getPrice());
        CartItemEntity savedCartItem = cartRepository.save(CartItemEntity.builder()
            .product(cartProduct)
            .quantity(1)
            .build());
        return toCartItem(savedCartItem);
    }

    @Override
    public void remove(UUID cartItemId) {
        Optional<CartItemEntity> cartItem = cartRepository.findById(cartItemId);
        cartItem.ifPresent(cartRepository::delete);
    }

    @Override
    public void removeAll() {
        cartRepository.deleteAll();
    }

    @Override
    public void checkOut() {
        List<CartItemEntity> cart = cartRepository.findAll();
        boolean purchaseSuccess = purchasesService.purchase(cart);
        if (purchaseSuccess) {
            cartRepository.deleteAll();
        }
    }
}
