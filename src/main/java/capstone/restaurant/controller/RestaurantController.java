package capstone.restaurant.controller;

import capstone.restaurant.dto.ResponseDto;
import capstone.restaurant.dto.restaurant.RestaurantListResponse;
import capstone.restaurant.dto.restaurant.RestaurantResponse;
import capstone.restaurant.dto.tag.TagListResponse;
import capstone.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@Tag(name = "restaurants", description = "식당 조회 API")
@RestController
@RequestMapping("api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {


    private final RestaurantService restaurantService;

    @Operation(summary = "식당 목록 조회", description = "조건에 맞는 식당의 목록을 조회한다.")
    @GetMapping("/tag")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<RestaurantListResponse> getRestaurantListByTag(@Parameter(example = "홍대", description = "조회하고 싶은 장소") @RequestParam(required = false) String place,
                                                                 @Parameter(example = "맛집", description = "조회하고 싶은 태그, 여러 태그를 조회하려면 여러번 쓴다.") @RequestParam(required = false) String[] tags,
                                                                 @Parameter(example = "1", description = "조회하려는 페이지 번호, 기본으로 1") @RequestParam(required = false, defaultValue = "1") int page) {

        RestaurantListResponse restaurantListResponse = this.restaurantService.restaurantListFindByTag(place , tags  , page);

        return new ResponseDto<>(200, "ok", restaurantListResponse);

    }

    @GetMapping("/keyword")
    public ResponseDto<RestaurantListResponse> getRestaurantListByKeyword(@Parameter(example = "롯데리아" , description = "검색 키워드") @RequestParam(required = false) String keyword,
                                                                          @Parameter(example = "1", description = "조회하려는 페이지 번호, 기본으로 1") @RequestParam(required = false, defaultValue = "1") int page){
        RestaurantListResponse restaurantListResponse=  this.restaurantService.restaurantListResponseByKeyword(keyword , page);
        return new ResponseDto<>(200, "ok" , restaurantListResponse);
    }

    @Operation(summary = "식당 상세 조회", description = "식당의 상세정보를 조회한다.")
    @GetMapping("/{id}")
    public ResponseDto<RestaurantResponse> getRestaurant(@PathVariable String id) {
        return new ResponseDto<>(200, "ok", new RestaurantResponse());
    }
}
