package Ecommerce.Initial_Project.dto.response;

import Ecommerce.Initial_Project.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartPaymentResponseDTO {
    private String cartId;

    private List<CartResponseDTO> cartList;
}
