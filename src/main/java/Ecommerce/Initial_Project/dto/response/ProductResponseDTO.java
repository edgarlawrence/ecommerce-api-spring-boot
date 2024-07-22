package Ecommerce.Initial_Project.dto.response;


import Ecommerce.Initial_Project.dto.request.ProductCategoryRequestDTO;
import Ecommerce.Initial_Project.model.ProductCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private String id;

    private String title;

    private String description;

    private Long price;

    private Long stock;

    private Boolean isDiscount;

    private Long discountPrice;

    private List<ProductCategoryResponseDTO> productCategoryList;
}
