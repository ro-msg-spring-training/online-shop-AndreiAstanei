package ro.msg.learning.shop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCart;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"orders"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String emailAddress;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shoppingCartId", referencedColumnName = "id")
    private ShoppingCart shoppingCart;
}
