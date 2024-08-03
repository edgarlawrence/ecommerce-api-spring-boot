package Ecommerce.Initial_Project.dto.request;

import Ecommerce.Initial_Project.model.PaymentHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentHistoryRequestDTO {
    private String paymentId;
    private String historyId;

    private List<PaymentRequestDTO> paymentList;
}
