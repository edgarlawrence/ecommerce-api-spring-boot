package Ecommerce.Initial_Project.repository;

import Ecommerce.Initial_Project.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    Optional<User> findByEmail(String email);
}