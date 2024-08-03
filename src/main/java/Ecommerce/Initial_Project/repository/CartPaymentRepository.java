package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.model.CartPayment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartPaymentRepository extends JpaRepository<CartPayment, String> {
    @Transactional
    void deleteCartPaymentByid(String paymentId);
}
