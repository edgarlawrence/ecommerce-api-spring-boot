package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
}
