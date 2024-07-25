package Ecommerce.Initial_Project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartPaymentRequestDTO {
    private String cartId;
    private String paymentId;
}
