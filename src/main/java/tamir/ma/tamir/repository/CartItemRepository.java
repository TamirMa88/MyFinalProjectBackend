package tamir.ma.tamir.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tamir.ma.tamir.entity.CartItem;
import tamir.ma.tamir.entity.Item;

public interface CartItemRepository extends JpaRepository<CartItem, Long> { }