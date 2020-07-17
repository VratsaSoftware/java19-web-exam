package com.vsc.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class PurchaseController extends BaseController {

    private final UserService userService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(UserService userService, ProductService productService, PurchaseService purchaseService) {
        this.userService = userService;
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView purchase(
            Principal principal,
            @CookieValue(value = "selectedProducts", defaultValue = "") String selectedProducts,
            HttpServletResponse response) throws NoProductsSelectedException {

        User user = userService.getUser(principal.getName());
        CartDTO cart = productService.getProductsFromCart(selectedProducts);
        purchaseService.purchase(user, cart);
        response.addCookie(resetProductsCookie());
        return redirect("/products");
    }

    private Cookie resetProductsCookie() {
        Cookie cookie = new Cookie("selectedProducts", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}
