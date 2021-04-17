package com.wty.one2one_foregin_04;

public class People01 {

    private Long id;

    private String name;

    private IdCard01 idCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdCard01 getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard01 idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "People01{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
