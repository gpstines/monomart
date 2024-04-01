package mart.mono.commerce.purchase;

import mart.mono.commerce.cart.CartItemEntity;
import mart.mono.inventory.lib.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PurchasesService {

    private final PurchasesRepository purchasesRepository;

    private final IProductService productService;

    public PurchasesService(PurchasesRepository purchasesRepository, IProductService productService) {
        this.purchasesRepository = purchasesRepository;
        this.productService = productService;
    }

    public List<Purchase> getAll() {
        return purchasesRepository.findAll();
    }

    public boolean purchase(List<CartItemEntity> cartItems) {
        try {
            purchasesRepository.save(new Purchase(UUID.randomUUID(), cartItems));
            cartItems.forEach(cartItem -> {
                productService.decrementProductQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
