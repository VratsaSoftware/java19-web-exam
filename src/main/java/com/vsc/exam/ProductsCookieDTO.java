package com.vsc.exam;

public class ProductsCookieDTO {

    private String cookieValue;
    private int selectedProductsCount;

    public ProductsCookieDTO() {
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public int getSelectedProductsCount() {
        return selectedProductsCount;
    }

    public void setSelectedProductsCount(int selectedProductsCount) {
        this.selectedProductsCount = selectedProductsCount;
    }
}
