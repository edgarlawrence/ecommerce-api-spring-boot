package Ecommerce.Initial_Project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Ecommerce.Initial_Project.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    @Transactional
    void deleteByProductId(String productId); // Custom method
}
