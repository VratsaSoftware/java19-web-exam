package com.vsc.exam;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private List<SelectedProductDTO> products;
    private Double total;

    public CartDTO() {
        this(new ArrayList<>(), 0D);
    }

    public CartDTO(List<SelectedProductDTO> products, Double total) {
        this.products = products;
        this.total = total;
    }

    public List<SelectedProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<SelectedProductDTO> products) {
        this.products = products;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
