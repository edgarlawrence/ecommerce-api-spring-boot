package Ecommerce.Initial_Project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryResponseDTO {
    private String categoryId;
    private String categoryTitle;
}
