package platform.codingnomads.co.cartmicroservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.codingnomads.co.cartmicroservice.model.Cart;
import platform.codingnomads.co.cartmicroservice.model.CartItem;
import platform.codingnomads.co.cartmicroservice.repository.CartRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {


    private final CartRepository cartRepository;

    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = Cart.builder().userId(userId).build();
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    public Integer getTotalItemsForUser(Long userId) {
        return cartRepository.getTotalItemsForUser(userId);
    }

    @Transactional
    public Cart addCartItem(Long itemId, Long userId) {
        Cart cart = getOrCreateCart(userId);

        boolean isExistingItem = false;
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(itemId)) {
                item.setAmount(item.getAmount() + 1);
                isExistingItem = true;
                break;
            }
        }
        if (!isExistingItem) {
            cart.addCartItem(itemId);
        }
        return cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = Cart.builder().userId(userId).build();
        }
        return cart;
    }

    @Transactional
    public Cart removeCartItem(Long cartItemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.removeCartItem(cartItemId);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateAmount(Long userId, Long cartItemId, Integer amount) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.getItems().stream().filter(i -> i.getId().compareTo(cartItemId) == 0)
                .findFirst().ifPresent(cartItem -> cartItem.setAmount(amount));
        return cart;
    }
}
