package Ecommerce.Initial_Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_payment")
public class CartPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
