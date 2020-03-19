package ro.msg.learning.shop.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private String addressCountry;
    @EqualsAndHashCode.Include
    private String addressCity;
    @EqualsAndHashCode.Include
    private String addressCounty;
    @EqualsAndHashCode.Include
    private String addressStreetAddress;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<Stock> stocks;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<Revenue> revenues;

    @ManyToMany(mappedBy = "shippedFrom", fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.SELECT)
    @ToString.Exclude
    private Set<Order> orders;
}
