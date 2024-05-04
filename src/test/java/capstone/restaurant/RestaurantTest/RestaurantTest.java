package capstone.restaurant.RestaurantTest;

import capstone.restaurant.dto.restaurant.RestaurantListResponse;
import capstone.restaurant.entity.Restaurant;
import capstone.restaurant.entity.RestaurantTag;
import capstone.restaurant.entity.Tag;
import capstone.restaurant.repository.RestaurantRepository;
import capstone.restaurant.repository.RestaurantTagRepository;
import capstone.restaurant.repository.TagRepository;
import capstone.restaurant.service.RestaurantService;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RestaurantTest {

    @Autowired
    public RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantTagRepository restaurantTagRepository;
    @Autowired
    public TagRepository tagRepository;
    @Autowired
    public RestaurantService restaurantService;


    @AfterEach
    public void afterEach(){
        restaurantTagRepository.deleteAll();
        restaurantRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    public void test_tag_query(){
        Restaurant restaurant1 = Restaurant.builder().name("abc").restaurantHash("13").build();
        Restaurant restaurant2 = Restaurant.builder().name("def").restaurantHash("12").build();

        Tag tag1 = Tag.builder().tagName("맛있어요").build();
        Tag tag2 = Tag.builder().tagName("분위기가 좋아요").build();
        Tag tag3 = Tag.builder().tagName("넓어요").build();

        RestaurantTag rt1 = new RestaurantTag();
        rt1.setRestaurant(restaurant1);
        rt1.setTag(tag1);

        RestaurantTag rt2 = new RestaurantTag();
        rt2.setRestaurant(restaurant1);
        rt2.setTag(tag2);

        RestaurantTag rt3 = new RestaurantTag();
        rt3.setRestaurant(restaurant2);
        rt3.setTag(tag1);

        RestaurantTag rt4 = new RestaurantTag();
        rt4.setRestaurant(restaurant2);
        rt4.setTag(tag3);

        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        restaurantTagRepository.save(rt1);
        restaurantTagRepository.save(rt2);
        restaurantTagRepository.save(rt3);
        restaurantTagRepository.save(rt4);

        String[] list  = {"맛있어요" , "넓어요"};

        RestaurantListResponse response = restaurantService.restaurantListFind("", list, 0);

        Assertions.assertThat(response.getRestaurants().get(0).getRestaurantId()).isEqualTo("12");

    }
}