package com.vsc.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CartRestController {

    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartRestController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping(value = "/cart/add", consumes = "application/json")
    public ResponseEntity<Integer> addProduct(
            @RequestBody AddProductDTO addProductDTO,
            @CookieValue(value = "selectedProducts", defaultValue = "") String selectedProducts,
            HttpServletResponse response) {

        ProductsCookieDTO dto = cartService.addProductToCookie(addProductDTO, selectedProducts);
        response.addCookie(constructCookie(dto.getCookieValue()));
        return ResponseEntity.ok(dto.getSelectedProductsCount());
    }

    @PostMapping(value = "/cart/update", consumes = "application/json")
    public ResponseEntity<CartDTO> removeProduct(
            @RequestBody RemoveProductDTO removeProductDTO,
            @CookieValue(value = "selectedProducts", defaultValue = "") String selectedProducts,
            HttpServletResponse response) {

        ProductsCookieDTO dto = cartService.updateProductFromCookie(removeProductDTO, selectedProducts, false);
        response.addCookie(constructCookie(dto.getCookieValue()));
        return getProductsInCart(dto);
    }

    @PostMapping(value = "/cart/discard", consumes = "application/json")
    public ResponseEntity<CartDTO> discardProduct(
            @RequestBody RemoveProductDTO removeProductDTO,
            @CookieValue(value = "selectedProducts", defaultValue = "") String selectedProducts,
            HttpServletResponse response) {

        ProductsCookieDTO dto = cartService.updateProductFromCookie(removeProductDTO, selectedProducts, true);
        response.addCookie(constructCookie(dto.getCookieValue()));
        return getProductsInCart(dto);
    }

    private ResponseEntity<CartDTO> getProductsInCart(ProductsCookieDTO productsCookieDTO) {
        CartDTO cart = productService.getProductsFromCart(productsCookieDTO.getCookieValue());
        return ResponseEntity.ok(cart);
    }

    private Cookie constructCookie(String cookieValue) {
        Cookie cookie = new Cookie("selectedProducts", cookieValue);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        return cookie;
    }
}
