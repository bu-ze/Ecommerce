package com.ecommerce.web.controller;

import com.ecommerce.web.model.Product;
import com.ecommerce.web.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PaypalController {
    private final PaypalService paypalService;

    String cancelUrl = "http://localhost:8080/payment/cancel";
    String successUrl = "http://localhost:8080/payment/success";

    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @GetMapping("/payment")
    public String CheckOutForm(Model model){
        model.addAttribute("product", new Product());
        return "payment";
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment(@ModelAttribute("product") Product product){
        try {
            Payment payment = paypalService.createPayment(product.getPrice(), product.getCurrency(), product.getMethod(), product.getIntent(), product.getDescription(), cancelUrl,  successUrl);
            for (Links links : payment.getLinks()){
                if (links.getRel().equals("approval_url")){
                    return new RedirectView(links.getHref());
                }
            }
        }catch (PayPalRESTException e){
            e.printStackTrace();
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")){
                return "";
            }
        }catch (PayPalRESTException e){
            e.printStackTrace();
        } return "success";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "cancel";
    }

    @GetMapping("/payment/error")
    public String paymentError(){
        return "error";
    }

}
