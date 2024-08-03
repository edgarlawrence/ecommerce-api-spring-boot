package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.model.PaymentHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, String> {
    @Transactional
    void deletePaymentHistoryById(String historyId);
}
