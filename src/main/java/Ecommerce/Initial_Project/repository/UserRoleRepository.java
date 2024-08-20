package Ecommerce.Initial_Project.repository;


import Ecommerce.Initial_Project.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    void deleteRoleById(Integer roleId);
}
