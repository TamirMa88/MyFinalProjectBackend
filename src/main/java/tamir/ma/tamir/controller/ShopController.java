package tamir.ma.tamir.controller;




import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tamir.ma.tamir.dto.OrderDto;
import tamir.ma.tamir.dto.ShopRequestDto;
import tamir.ma.tamir.dto.ShopResponseDto;
import tamir.ma.tamir.service.OrderService;
import tamir.ma.tamir.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ShopResponseDto> createItem(
            @RequestBody @Valid ShopRequestDto dto, UriComponentsBuilder uriBuilder) {
        var saved = shopService.createItem(dto);
        var uri = uriBuilder.path("/api/v1/shop/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ShopResponseDto>> getAllItems() {
        return ResponseEntity.ok(shopService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopResponseDto> getItemById(
            @Valid @NotNull @PathVariable long id) {
        return ResponseEntity.ok(shopService.getItemById(id));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    //api/v1/posts/3
    @PatchMapping("/update/{id}")
    public ResponseEntity<ShopResponseDto> updateItemById(
            @Valid @NotNull @PathVariable long id,
            @Valid @RequestBody tamir.ma.tamir.dto.ShopRequestDto dto) {
        return ResponseEntity.ok(shopService.updateItemById(dto, id));
    }


    @PostMapping("/create-order")
    public ResponseEntity<OrderDto> createOrder(
            @Valid @RequestBody tamir.ma.tamir.dto.OrderDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    //DELETE api/v1/posts/3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ShopResponseDto> deleteItemById(
            @Valid @NotNull @PathVariable long id) {
        System.out.println("Reach");
        return ResponseEntity.ok(shopService.deleteItemById(id));
    }



}
