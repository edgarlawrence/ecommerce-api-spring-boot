package Ecommerce.Initial_Project.dto.response;

import Ecommerce.Initial_Project.model.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpResponseDTO {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String password;
    private List<UserRoleResponseDTO> userRoleList;
}
