package com.wty.two_many2many_07;

import java.util.Set;

public class Category02 {

    private Long id;

    private String name;

    private Set<Item02> items;

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

    public Set<Item02> getItems() {
        return items;
    }

    public void setItems(Set<Item02> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Category02{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
