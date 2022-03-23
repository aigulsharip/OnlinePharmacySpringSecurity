package kz.aigulsharip.springboot.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String dosage;
    private String form;
    private int price;
    private int quantity;
    private String availability;
    private String image;


    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;


}
