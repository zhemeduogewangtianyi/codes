package com.wty.one2one_foregin_04;

public class IdCard01 {

    private Long id;

    private String num;

    private People01 people;

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

    public People01 getPeople() {
        return people;
    }

    public void setPeople(People01 people) {
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
