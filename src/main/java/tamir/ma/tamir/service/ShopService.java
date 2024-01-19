package tamir.ma.tamir.service;



import tamir.ma.tamir.dto.OrderDto;
import tamir.ma.tamir.dto.ShopRequestDto;
import tamir.ma.tamir.dto.ShopResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {
//הצגת מוצרים
    ShopResponseDto createItem(@Valid ShopRequestDto postRequestDto);

    List<ShopResponseDto> getAllItems();

    //get post by id:
    ShopResponseDto getItemById(long id);

    ShopResponseDto updateItemById(ShopRequestDto dto, long id);

    ShopResponseDto deleteItemById(long id);

   // ShopPageResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
