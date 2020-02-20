package ro.msg.learning.shop.Entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "order_locations",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @Fetch(value = FetchMode.SELECT)
    private Set<Location> shippedFrom;

    @ManyToOne
    private Customer customer;

    @EqualsAndHashCode.Include
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Include
    private Integer order_timestamp;

    @EqualsAndHashCode.Include
    private String addressCountry;

    @EqualsAndHashCode.Include
    private String addressCity;

    @EqualsAndHashCode.Include
    private String addressCounty;

    @EqualsAndHashCode.Include
    private String addressStreetAddress;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
