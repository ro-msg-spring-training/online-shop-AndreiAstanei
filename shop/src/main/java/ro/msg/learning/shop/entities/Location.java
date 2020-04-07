package ro.msg.learning.shop.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"stocks", "revenues", "orders"})
@ToString(exclude = {"stocks", "revenues", "orders"})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = "address_country")),
            @AttributeOverride(name = "county", column = @Column(name = "address_county")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "streetAddress", column = @Column(name = "address_street_address"))
    })
    private EmbeddableAddress embeddableAddress;

    @OneToMany(mappedBy = "location")
    private List<Stock> stocks;

    @OneToMany(mappedBy = "location")
    private List<Revenue> revenues;

    @ManyToMany(mappedBy = "shippedFrom", fetch = FetchType.EAGER)
    private Set<Order> orders;
}
