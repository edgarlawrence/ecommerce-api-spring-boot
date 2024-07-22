package Ecommerce.Initial_Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Ecommerce.Initial_Project.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
}
