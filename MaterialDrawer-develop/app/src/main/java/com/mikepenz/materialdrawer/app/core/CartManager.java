package com.mikepenz.materialdrawer.app.core;

import com.mikepenz.materialdrawer.app.entity.Cart;

import java.math.BigDecimal;

/**
 * Created by HP on 01-12-2015.
 */
public class CartManager {

    public static CartManager _cartManger;
    private static Cart _cart = new Cart();

    private CartManager() {
    }

    public static CartManager instance() {
        if (_cartManger == null) {
            _cartManger = new CartManager();
        }
        return _cartManger;
    }

    public static void saveCart(Cart cart) {
        _cart = cart;
    }

    public Cart getCart() {
        return _cart;
    }

    public static void clearCart() {
        if (_cart.getCartItems() != null) {
//            _cart.clearCartItem();
        }
        _cart.setTotalCheckoutAmount(new BigDecimal(0));
    }

}
