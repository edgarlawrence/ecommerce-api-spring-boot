package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p JOIN p.productCategoryList pc JOIN pc.category c WHERE c.id = :categoryId")
    List<Product> findAllByCategoryId(String categoryId);
}
