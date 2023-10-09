package platform.codingnomads.co.cartmicroservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.codingnomads.co.cartmicroservice.service.CartService;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.getCartByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/items-count", method = RequestMethod.GET)
    public ResponseEntity<?> getTotalItemsForUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.getTotalItemsForUser(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addNewCartItem(@RequestParam("item-id") Long itemId, @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.addCartItem(itemId, userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable("cartItemId") Long cartItemId,
                                            @PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(cartService.removeCartItem(cartItemId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{userId}/{cartItemId}")
    public ResponseEntity<?> changeItemAmount(@PathVariable("userId") Long userId,
                                              @PathVariable("cartItemId") Long cartItemId,
                                              @RequestParam("amount-change") Integer amountChange) {
        try {
            return ResponseEntity.ok(cartService.changeItemAmountOrRemove(userId, cartItemId, amountChange));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
