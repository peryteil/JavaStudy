package dto;

public class ProductDto {
    private int pId;
    private String prouductName;
    private int price;
    private int stock;
    private boolean status = true;

    public ProductDto() {
    }

    public ProductDto(int pId, String prouductName, int price, int stock, boolean status) {
        this.pId = pId;
        this.prouductName = prouductName;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getProuductName() {
        return prouductName;
    }

    public void setProuductName(String prouductName) {
        this.prouductName = prouductName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setProductName(String productName) {
    }
}
