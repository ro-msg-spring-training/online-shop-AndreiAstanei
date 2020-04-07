package ro.msg.learning.shop.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Order_")
@Builder
@EqualsAndHashCode(exclude = {"shippedFrom", "orderDetails"})
@ToString(exclude = {"shippedFrom", "orderDetails"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_locations",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> shippedFrom;

    @ManyToOne
    private Customer customer;

    private LocalDateTime createdAt;

    private Integer order_timestamp;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = "address_country")),
            @AttributeOverride(name = "county", column = @Column(name = "address_county")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "streetAddress", column = @Column(name = "address_street_address"))
    })
    private EmbeddableAddress embeddableAddress;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;
}
