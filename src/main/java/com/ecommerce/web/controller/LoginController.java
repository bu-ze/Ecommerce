package com.ecommerce.web.controller;

import com.ecommerce.web.model.User;
import com.ecommerce.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttributes, HttpSession session){
        if (result.hasErrors()){
            return "login";
        }
        try {
            User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
            session.setAttribute("user", loggedInUser);
            redirectAttributes.addFlashAttribute("success", "Login successful");
            return "redirect:/products";
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }

}
