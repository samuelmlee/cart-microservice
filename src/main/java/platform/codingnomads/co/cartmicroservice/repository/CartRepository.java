package platform.codingnomads.co.cartmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platform.codingnomads.co.cartmicroservice.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userid);

    @Query("SELECT coalesce(SUM(ci.amount), 0) FROM Cart c LEFT JOIN c.items ci WHERE c.userId = :userId")
    Integer getTotalItemsForUser(@Param("userId") Long userId);


}
