package ro.msg.learning.shop.entities.shoppingCartEntities;

import lombok.*;
import ro.msg.learning.shop.entities.Customer;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"shoppingCartProducts", "customer"})
@EqualsAndHashCode(exclude = {"shoppingCartProducts", "customer"})
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "shoppingCart")
    private Customer customer;

    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.EAGER)
    private List<ShoppingCartItem> shoppingCartProducts;
}
