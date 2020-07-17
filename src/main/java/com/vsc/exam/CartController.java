package com.vsc.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController extends BaseController {

    private final ProductService productService;

    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/cart")
    public ModelAndView showCart(@CookieValue(value = "selectedProducts", defaultValue = "") String selectedProducts) {
        CartDTO cart = productService.getProductsFromCart(selectedProducts);
        return send("cart", "cart", cart);
    }
}
