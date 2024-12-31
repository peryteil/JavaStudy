package dto;

public class ProductDto {
    private int pId;
    private String prouductName;
    private int price;
    private int stock;
    private boolean status = true;

    public ProductDto() {
    }

    public ProductDto(String prouductName, int price, int stock, boolean status) {
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "pId=" + pId +
                ", prouductName='" + prouductName + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }

}
