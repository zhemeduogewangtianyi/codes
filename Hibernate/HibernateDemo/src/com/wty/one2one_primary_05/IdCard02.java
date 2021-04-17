package com.wty.one2one_primary_05;

public class IdCard02 {

    private Long id;

    private String num;

    private People02 people;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public People02 getPeople() {
        return people;
    }

    public void setPeople(People02 people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "IdCard01{" +
                "id=" + id +
                ", num='" + num + '\'' +
                '}';
    }
}
