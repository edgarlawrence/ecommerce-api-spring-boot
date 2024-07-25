package Ecommerce.Initial_Project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {
    private String id;
    private Long totalAmount;
    private List<ProductCartResponseDTO> productCartList;
}
