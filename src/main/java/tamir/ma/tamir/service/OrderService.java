package tamir.ma.tamir.service;

import jakarta.validation.Valid;
import tamir.ma.tamir.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrders();
    OrderDto createOrder(@Valid  OrderDto orderDto);
}
