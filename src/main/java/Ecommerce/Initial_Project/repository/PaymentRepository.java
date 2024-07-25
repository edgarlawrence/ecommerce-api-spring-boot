package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.dto.response.PaymentProjection;
import Ecommerce.Initial_Project.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query(value = "SELECT p.id as paymentId, p.payment_complete as paymentComplete, " +
            "c.id as cartId, prod.id as productId, prod.title as productName, " +
            "prod.price as productPrice, prod.amount as productAmount " +
            "FROM payment p " +
            "JOIN cart_payment cp ON p.id = cp.payment_id " +
            "JOIN cart c ON cp.cart_id = c.id " +
            "JOIN product_cart pc ON c.id = pc.cart_id " +
            "JOIN product prod ON pc.product_id = prod.id",
            nativeQuery = true)
    List<PaymentProjection> findAllPaymentsWithCartAndProducts();
}
