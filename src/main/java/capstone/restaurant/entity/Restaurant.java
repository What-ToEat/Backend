package capstone.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name= "RESTAURANT_SEQ_GEN",
        sequenceName = "RESTAURANT_SEQ")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESTAURANT_SEQ_GEN")
    private Long id;

    @Column(unique = true)
    private String restaurantHash;

    private String address;

    private String thumbnail;

    private String place;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantTag> restaurantTag = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "restaurant")
    private  List<Review> reviews = new ArrayList<>();

}
