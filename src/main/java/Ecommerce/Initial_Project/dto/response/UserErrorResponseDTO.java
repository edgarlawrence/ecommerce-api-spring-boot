package Ecommerce.Initial_Project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserErrorResponseDTO {
    private HttpStatus httpStatus;
    private String message;
}
