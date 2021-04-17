package com.wty.two_many2many_07;

import java.util.Set;

public class Item02 {

    private Long id;

    private String name;

    private Set<Category02> categorys;

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

    public Set<Category02> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<Category02> categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        return "Item02{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
