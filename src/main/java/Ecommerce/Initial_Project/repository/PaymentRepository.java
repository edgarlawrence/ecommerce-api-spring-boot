package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
