package com.vsc.exam;

import com.vsc.exam.CartDTO;
import com.vsc.exam.SelectedProductDTO;
import com.vsc.exam.Purchase;
import com.vsc.exam.User;
import com.vsc.exam.NoProductsSelectedException;
import com.vsc.exam.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public boolean purchase(User user, CartDTO cart) throws NoProductsSelectedException {
        if (cart.getProducts() == null || cart.getProducts().isEmpty()) {
            throw new NoProductsSelectedException("The cart is empty");
        }
        for (SelectedProductDTO selectedProduct : cart.getProducts()) {
            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setProduct(selectedProduct.getProduct());
            purchase.setPurchaseDate(new Date());
            purchase.setCount(selectedProduct.getCount());
        }

        return true;
    }
}
