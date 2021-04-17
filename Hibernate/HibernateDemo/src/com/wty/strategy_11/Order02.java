package com.wty.strategy_11;

public class Order02 {

    private Long id;
    private String orderName;

    private Consumer02 consumer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Consumer02 getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer02 consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", consumer=" + consumer +
                '}';
    }
}
