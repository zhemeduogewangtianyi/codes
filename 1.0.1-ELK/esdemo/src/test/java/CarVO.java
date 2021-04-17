public class CarVO {

    private Long price;
    private String color;
    private String make;
    private String sold;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "CarVO{" +
                "price=" + price +
                ", color='" + color + '\'' +
                ", make='" + make + '\'' +
                ", sold='" + sold + '\'' +
                '}';
    }
}
