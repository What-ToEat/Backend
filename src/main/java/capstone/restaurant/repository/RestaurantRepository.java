package capstone.restaurant.repository;

import capstone.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> , RestaurantRepositoryCustom {
    @Query(value = "SELECT * FROM Restaurant ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Restaurant findRandomRestaurant();

    Page<Restaurant> findRestaurantsByNameContaining(String name , Pageable pageable);

    Restaurant findByRestaurantHash(String restaurantHash);


}
