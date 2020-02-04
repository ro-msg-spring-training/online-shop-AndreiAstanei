package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Order_")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @ManyToOne
    private Location ShippedFrom;

    @ManyToOne
    private Customer Customer;

    private LocalDateTime CreatedAt;
    private String AddressCountry;
    private String AddressCity;
    private String AddressCounty;
    private String AddressStreetAddress;

    @OneToMany(mappedBy = "Order")
    private List<OrderDetail> orderDetails;
}
