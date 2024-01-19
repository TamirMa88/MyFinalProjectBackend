package tamir.ma.tamir.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tamir.ma.tamir.entity.Item;

public interface ShopRepository extends JpaRepository<Item, Long> { }