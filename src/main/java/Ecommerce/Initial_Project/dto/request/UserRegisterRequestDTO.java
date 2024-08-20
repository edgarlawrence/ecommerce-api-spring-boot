package Ecommerce.Initial_Project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequestDTO {
    private String email;

    private String username;

    private String password;

    private String fullname;

    private List<UserRoleRequestDTO> userRoleRequestDTOList;
}
