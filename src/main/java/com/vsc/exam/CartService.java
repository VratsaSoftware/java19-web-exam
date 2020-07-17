package com.vsc.exam;

import com.vsc.exam.AddProductDTO;
import com.vsc.exam.ProductsCookieDTO;
import com.vsc.exam.RemoveProductDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    public ProductsCookieDTO addProductToCookie(AddProductDTO addProductDTO, String cookie) {
        if (cookie.isEmpty()) {
            ProductsCookieDTO productsCookie = new ProductsCookieDTO();
            productsCookie.setCookieValue(addProductDTO.getProductId() + "=" + addProductDTO.getQuantity());
            productsCookie.setSelectedProductsCount(1);
            return productsCookie;
        }

        Map<String, Integer> products = new HashMap<>();
        String[] productPairs = cookie.split("\\|");
        for (String productPair : productPairs) {
            String[] values = productPair.split("=");
            int count = Integer.parseInt(values[1]);
            products.merge(values[0], count, (currentCount, s2) -> currentCount + count);
        }

        products.merge(addProductDTO.getProductId() + "", addProductDTO.getQuantity(), (currentCount, s2) -> currentCount + addProductDTO.getQuantity());

        return getResult(products);
    }

    public ProductsCookieDTO updateProductFromCookie(RemoveProductDTO removeProductDTO, String cookie, boolean shouldRemoveAll) {
        if (cookie.isEmpty()) {
            ProductsCookieDTO productsCookie = new ProductsCookieDTO();
            productsCookie.setCookieValue("");
            productsCookie.setSelectedProductsCount(0);
            return productsCookie;
        }

        Map<String, Integer> products = new HashMap<>();
        String[] productPairs = cookie.split("\\|");
        for (String productPair : productPairs) {
            String[] values = productPair.split("=");
            int count = Integer.parseInt(values[1]);
            String productId = values[0];

            products.put(productId, count);
            if (!productId.equals(removeProductDTO.getProductId().toString())) {
                continue;
            }

            if (shouldRemoveAll || removeProductDTO.getQuantity() == null || removeProductDTO.getQuantity() < 1) {
                products.remove(productId);
            } else {
                products.put(productId, Math.abs(removeProductDTO.getQuantity()));
                if (products.get(productId) < 1) {
                    products.remove(productId);
                }
            }
        }

        return getResult(products);
    }

    private ProductsCookieDTO getResult(Map<String, Integer> products) {
        StringBuilder sb = new StringBuilder();
        products.forEach((k, v) -> sb.append(k).append("=").append(v).append("|"));
        int totalCount = products.values().stream()
                .mapToInt((v) -> v)
                .sum();

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        ProductsCookieDTO productsCookie = new ProductsCookieDTO();
        productsCookie.setCookieValue(sb.toString());
        productsCookie.setSelectedProductsCount(totalCount);

        return productsCookie;
    }
}
