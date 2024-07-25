package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.model.ProductCart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, String> {
    @Transactional
    void deleteByCartId(String productId); // Custom method
}
