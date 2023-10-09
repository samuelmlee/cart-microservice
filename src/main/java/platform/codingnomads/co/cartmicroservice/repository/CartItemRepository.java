package platform.codingnomads.co.cartmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.codingnomads.co.cartmicroservice.model.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartUserIdAndItemId(Long userId, Long itemId);

    void deleteByCartUserIdAndItemId(Long userId, Long itemId);
}
