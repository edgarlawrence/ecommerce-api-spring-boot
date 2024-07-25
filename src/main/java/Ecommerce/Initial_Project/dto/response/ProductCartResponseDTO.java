package Ecommerce.Initial_Project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCartResponseDTO {
    private String productId;
    private String productName;
    private Long productPrice;
    private Long productAmount;
}
