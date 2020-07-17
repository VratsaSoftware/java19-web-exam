package com.vsc.exam;

import com.vsc.exam.CartDTO;
import com.vsc.exam.Product;
import com.vsc.exam.ProductRepository;
import com.vsc.exam.SelectedProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public CartDTO getProductsFromCart(String productsCookie) {
        CartDTO cart = new CartDTO();
        if (productsCookie.isEmpty()) return cart;

        List<SelectedProductDTO> selectedProducts = new ArrayList<>();
        double total = 0D;
        String[] productsPair = productsCookie.split("\\|");
        for (String pair : productsPair) {
            String[] productValues = pair.split("=");

            long productId = Long.parseLong(productValues[0]);
            Optional<Product> product = productRepository.findById(productId);
            if (!product.isPresent()) continue;

            SelectedProductDTO selectedProduct = new SelectedProductDTO();
            selectedProduct.setProduct(product.get());

            int count = Integer.parseInt(productValues[1]);
            selectedProduct.setCount(count);

            double cost = product.get().getPrice() * count;
            selectedProduct.setCost(cost);
            total += cost;

            selectedProducts.add(selectedProduct);
        }

        cart.setProducts(selectedProducts);
        cart.setTotal(total);

        return cart;
    }

    @PostConstruct
    private void initProducts() {
        Map<String, Double> products = populateProductsMap();
        for (Map.Entry<String, Double> productPair : products.entrySet()) {
            if (!productRepository.existsByName(productPair.getKey())) {
                Product product = new Product();
                product.setName(productPair.getKey());
                product.setPrice(productPair.getValue());
                productRepository.save(product);
            }
        }
    }

    private  Map<String, Double> populateProductsMap() {
        Map<String, Double> products = new HashMap<>();
        products.put("Milk", 2.49D);
        products.put("Bread", 1.09D);
        products.put("Meat", 5.89D);
        products.put("Bananas", 2.99D);
        products.put("Coffee", 3.79D);
        products.put("Ice cream", 3.99D);
        products.put("Cheese", 6.38D);

        return products;
    }

}
