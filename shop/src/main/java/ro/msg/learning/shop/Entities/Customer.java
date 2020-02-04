package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private String FirstName;
    private String LastName;
    private String Username;
    private String Password;
    private String EmailAddress;

    @OneToMany(mappedBy = "Customer")
    private List<Order> orders;
}
