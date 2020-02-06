package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreetAddress;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<Stock> stocks;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<Revenue> revenues;

    @OneToMany(mappedBy = "shippedFrom")
    @ToString.Exclude
    private List<Order> orders;
}
