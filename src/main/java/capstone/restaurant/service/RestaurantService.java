package capstone.restaurant.service;

import capstone.restaurant.dto.restaurant.RestaurantListResponse;
import capstone.restaurant.dto.restaurant.RestaurantListSub;
import capstone.restaurant.dto.restaurant.RestaurantResponse;
import capstone.restaurant.dto.restaurant.ReviewListSub;
import capstone.restaurant.dto.tag.TagResponse;
import capstone.restaurant.entity.Restaurant;
import capstone.restaurant.entity.RestaurantTag;
import capstone.restaurant.entity.Review;
import capstone.restaurant.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantResponse findRestaurantById(String restaurantId) {

        Restaurant restaurant = this.restaurantRepository.findByRestaurantHash(restaurantId);

        if(restaurant == null){
            throw new EntityNotFoundException("해당 레스토랑을 찾을 수 없습니다");
        }

        return Restaurant.toDto(restaurant);
    }

    public RestaurantResponse findRandomRestaurant() {
        Restaurant restaurant = restaurantRepository.findRandomRestaurant();
        return Restaurant.toDto(restaurant);
    }

    @Transactional
    public RestaurantListResponse findRestaurantListByTag(String place , String[] tags , int page){

        RestaurantListResponse response = new RestaurantListResponse();
        response.setRestaurants(this.restaurantRepository.findRestaurantListByTag(place , tags , page));
        return response;
    }

    @Transactional
    public RestaurantListResponse findRestaurantListByKeyword(String keyword , Integer page){
        RestaurantListResponse response = new RestaurantListResponse();

        Page<Restaurant> restaurantList = this.restaurantRepository.findRestaurantsByNameContaining(keyword, PageRequest.of(page - 1 , 10));
        response.setRestaurants(convertRestaurantsToRestaurantList(restaurantList));
        return response;
    }

    private List<RestaurantListSub> convertRestaurantsToRestaurantList(Page<Restaurant> restaurantList){

        List<RestaurantListSub> restaurantListSubs = new ArrayList<RestaurantListSub>();

        for(Restaurant restaurant : restaurantList){
            RestaurantListSub restaurantListSub = new RestaurantListSub();
            List<TagResponse> tagResponseList = new ArrayList<TagResponse>();

            restaurantListSub.setName(restaurant.getName());
            restaurantListSub.setThumbnail(restaurant.getThumbnail());
            restaurantListSub.setRestaurantId(restaurant.getRestaurantHash());

            for (RestaurantTag restaurantTag : restaurant.getRestaurantTag()) {
                tagResponseList.add(new TagResponse(restaurantTag.getTag().getTagName() , restaurantTag.getTag().getTagCategory().getCategoryName()));
            }

            restaurantListSub.setTags(tagResponseList);
            restaurantListSubs.add(restaurantListSub);

        }
        return restaurantListSubs;
    }
}
