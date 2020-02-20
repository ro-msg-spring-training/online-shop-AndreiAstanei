package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
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
    private List<Stock> stocks;

    @OneToMany(mappedBy = "location")
    private List<Revenue> revenues;

    @ManyToMany(mappedBy = "shippedFrom")
    @Fetch(value = FetchMode.SELECT)
    private Set<Order> orders;
}
