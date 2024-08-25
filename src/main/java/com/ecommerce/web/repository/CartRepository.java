package com.ecommerce.web.repository;

import com.ecommerce.web.model.Cart;
import com.ecommerce.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
}
