package mart.mono.inventory.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
record PurchaseEvent (
    @JsonProperty("productId") UUID productId,
    @JsonProperty("quantity") Integer quantity) {
}
