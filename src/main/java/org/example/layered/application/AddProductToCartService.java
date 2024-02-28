package org.example.layered.application;

import org.example.layered.domain.Cart;
import org.example.layered.domain.LineItem;
import org.example.layered.infra.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddProductToCartService {
    private final CartRepository cartRepository;

    public AddProductToCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Long addProduct(Long productId, Long optionId, Integer quantity) {
        Cart cart = new Cart();
        LineItem lineItem = new LineItem(productId, optionId, quantity);
        cart.addProduct(lineItem);

        Cart savedCart = cartRepository.save(cart);
        return savedCart.getId();
    }

    @Transactional
    public Long updateProduct(Long cartId, Long productId, Long optionId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("cart not found"));
        LineItem lineItem = new LineItem(productId, optionId, quantity);

        cart.addProduct(lineItem);

        return cart.getId();
    }
}