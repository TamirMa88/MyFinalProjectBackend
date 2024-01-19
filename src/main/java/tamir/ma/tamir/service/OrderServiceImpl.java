package tamir.ma.tamir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tamir.ma.tamir.dto.OrderDto;
import tamir.ma.tamir.dto.ShopRequestCartDto;
import tamir.ma.tamir.dto.ShopRequestDto;
import tamir.ma.tamir.entity.CartItem;
import tamir.ma.tamir.entity.Item;
import tamir.ma.tamir.entity.Order;
import tamir.ma.tamir.entity.User;
import tamir.ma.tamir.repository.CartItemRepository;
import tamir.ma.tamir.repository.OrderRepository;
import tamir.ma.tamir.repository.ShopRepository;
import tamir.ma.tamir.repository.UserRepository;

import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;


    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        List<Item> items = new ArrayList<>(shopRepository.findAll());
        HashMap<Long, Item> itemMap = new HashMap<>();
        for(var item : items) {
            itemMap.put(item.getId(),item);
        }

        Set<CartItem> cartItems = new HashSet<>();
        Order order = new Order();

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
        for(var item  : orderDto.getItems()) {
            if(itemMap.containsKey(item.getItemId())) {
                var cartItem = CartItem.builder()
                        .quantity(item.getQuantity())
                        .item(itemMap.get(item.getItemId()))
                        .build();
                cartItems.add(cartItem);
            }
        }

        order.setItems(cartItems);
        order.setAddress(orderDto.getAddress());
        user.getOrders().add(order);
        userRepository.save(user);

        return orderDto;
    }
    @Override
    public List<OrderDto> getOrders() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders =  orderRepository.findByUserEmail(email);
        List<OrderDto> orderDtos = new ArrayList<>();
        for(var order: orders) {
            List<ShopRequestCartDto> items = new ArrayList<>();
            for(var item: order.getItems()) {
                ShopRequestCartDto cartDto = new ShopRequestCartDto();
                cartDto.setPrice(item.getItem().getPrice());
                cartDto.setTitle(item.getItem().getTitle());
                cartDto.setBarcode(item.getItem().getBarcode());
                cartDto.setDescription(item.getItem().getDescription());
                cartDto.setItemId(item.getItem().getId());
                cartDto.setQuantity(item.getQuantity());

                items.add(cartDto);
            }
            OrderDto orderDto = OrderDto.builder().address(order.getAddress()).items(items).build();
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}
