package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @ManyToOne
    private Order Order;

    @ManyToOne
    private Product Product;
    private Integer Quantity;
}
