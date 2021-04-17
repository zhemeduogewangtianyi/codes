package com.wty.one2one_primary_05;

public class People02 {

    private Long id;

    private String name;

    private IdCard02 idCard;

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

    public IdCard02 getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard02 idCard) {
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
