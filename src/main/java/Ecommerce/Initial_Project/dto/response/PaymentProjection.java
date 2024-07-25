package Ecommerce.Initial_Project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface PaymentProjection {
    String getPaymentId();
    Boolean getPaymentComplete();
    String getCartId();
    String getProductId();
    String getProductName();
    Long getProductPrice();
    Long getProductAmount();
}
