package com.ecommerce.web.controller;

import com.ecommerce.web.model.Cart;
import com.ecommerce.web.model.User;
import com.ecommerce.web.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CartController {
    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @GetMapping("")
    public String viewCart(User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        List<Cart> cartList = cartRepository.findByUser(user);
        model.addAttribute("cartList", cartList);
        model.addAttribute("total", calculateTotal(cartList));
    }

    private Double calculateTotal(List<Cart> cartList) {
        return cartList.stream()
                .mapToDouble()
                .sum();
    }

    @PostMapping("/checkout")
    public String checkout(User user) {
        if (user == null) {
            return "redirect:/login";
        }

        return "redirect:/paypal-checkout";
    }
}
