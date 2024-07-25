package Ecommerce.Initial_Project.dto.response;

import Ecommerce.Initial_Project.dto.request.CartPaymentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {
    private String id;
    private Boolean paymentComplete;
    private List<CartPaymentResponseDTO> cartPaymentResponseList;
}
