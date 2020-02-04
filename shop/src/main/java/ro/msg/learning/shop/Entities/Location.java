package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Stock> stocks;

    @OneToMany(mappedBy = "location")
    private List<Revenue> revenues;

    @OneToMany(mappedBy = "shippedFrom")
    private List<Order> orders;
}
