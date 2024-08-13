package com.ecommerce.web.controller;

import com.ecommerce.web.model.Product;
import com.ecommerce.web.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String showProductsPage(){
        return "products";
    }

    @GetMapping("/products/list")
    public String listProducts(Model model){
        List<Product> productList = productService.getALLProducts();
        model.addAttribute("productlist", productList);
        return "products";
    }

    //@PostMapping("/")

}
