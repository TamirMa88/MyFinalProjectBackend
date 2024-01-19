package tamir.ma.tamir.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tamir.ma.tamir.entity.Order;
import tamir.ma.tamir.entity.Role;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserEmail(String userEmail);

}