package mart.mono.commerce.cart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mart.mono.commerce.product.ProductEntity;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
public class CartItemEntity {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="product_id", nullable = false)
    private ProductEntity product;

    private Integer quantity;
}
