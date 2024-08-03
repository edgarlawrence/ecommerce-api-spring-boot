package Ecommerce.Initial_Project.dto.request;

import Ecommerce.Initial_Project.model.ProductCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
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
public class ProductRequestDTO {
    private String id;
    private String title;
    private String desc;
    private Long price;
    private Long amount;
    private Long stock;
    private Boolean isDiscount;
    private Long discountPrice;
    private MultipartFile image;

    private List<ProductCategoryRequestDTO> productCategoryList;
}
