package Ecommerce.Initial_Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartPayment extends JpaRepository<CartPayment, String> {
    void deleteCartPaymentByid(String id);
}
