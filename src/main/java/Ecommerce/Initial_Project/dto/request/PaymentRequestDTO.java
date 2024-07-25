package Ecommerce.Initial_Project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {
    private String id;
    private Boolean paymentComplete;
    private List<CartPaymentRequestDTO> cartPaymentRequestList;
}
