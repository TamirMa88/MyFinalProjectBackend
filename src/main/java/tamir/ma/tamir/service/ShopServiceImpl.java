package tamir.ma.tamir.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tamir.ma.tamir.dto.OrderDto;
import tamir.ma.tamir.dto.ShopRequestDto;
import tamir.ma.tamir.dto.ShopResponseDto;
import tamir.ma.tamir.entity.CartItem;
import tamir.ma.tamir.entity.Item;
import tamir.ma.tamir.entity.Order;
import tamir.ma.tamir.error.ResourceNotFoundException;
import tamir.ma.tamir.repository.CartItemRepository;
import tamir.ma.tamir.repository.OrderRepository;
import tamir.ma.tamir.repository.ShopRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {
    private final ModelMapper modelMapper;
    private final ShopRepository shopRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShopResponseDto createItem(@Valid ShopRequestDto shopRequestDto) {
        //convert requestDTO To Entity
        var entity = modelMapper.map(shopRequestDto, Item.class);
        //save the entity
        var saved = shopRepository.save(entity);
     //convert saved entity to ResponseDto
        return modelMapper.map(saved, ShopResponseDto.class);
    }



    @Override
    public List<ShopResponseDto> getAllItems() {
        return  shopRepository
                .findAll()
                .stream()
                .map(p -> modelMapper.map(p, ShopResponseDto.class))
                .toList();
    }

    @Override
    public ShopResponseDto getItemById(long id) {
        return modelMapper.map(getItemEntity(id),  ShopResponseDto.class);
    }

    //dont copy paste -> reuse
    private Item getItemEntity(long id) {
        return shopRepository.findById(id).orElseThrow(
                () ->  new ResourceNotFoundException("Item", id)
        );
    }

    @Override
    public ShopResponseDto updateItemById(ShopRequestDto dto, long id) {
        Item product = getItemEntity(id); //assert that id exists

        //update => copy new props from request dto
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImg(dto.getImg());
        product.setBarcode(dto.getBarcode());
        //save
        var saved = shopRepository.save(product);
        //return response dto
        return modelMapper.map(saved, ShopResponseDto.class);
    }

    @Override
    public ShopResponseDto deleteItemById(long id) {
        val deleted = shopRepository.findById(id);
        shopRepository.deleteById(id);
        return modelMapper.map(deleted, ShopResponseDto.class);
    }
}
