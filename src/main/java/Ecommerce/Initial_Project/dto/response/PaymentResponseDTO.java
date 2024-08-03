package Ecommerce.Initial_Project.dto.response;

import Ecommerce.Initial_Project.dto.request.CartPaymentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {
    private String id;
    private Boolean paymentComplete;
    private String image;
    private List<CartPaymentResponseDTO> cartPaymentResponseList;
}
