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
    private Integer Id;

    private String Name;
    private String AddressCountry;
    private String AddressCity;
    private String AddressCounty;
    private String AddressStreetAddress;

    @OneToMany(mappedBy = "location")
    private List<Stock> stocks;

    @OneToMany(mappedBy = "location")
    private List<Revenue> revenues;

    @OneToMany(mappedBy = "ShippedFrom")
    private List<Order> orders;
}
