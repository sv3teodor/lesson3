public class Cart {
    private Products[] products;
    private Double total_price;
    private Double total_discount;

    public Cart(Products[] products, double total_price, double total_discount) {
        this.products = products;
        this.total_price = total_price;
        this.total_discount = total_discount;
    }

    public Products[] getProducts() {
        return products;
    }

    public void setProducts(Products[] products) {
        this.products = products;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }
}
