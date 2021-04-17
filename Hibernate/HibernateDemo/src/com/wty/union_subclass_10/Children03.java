package com.wty.union_subclass_10;

public class Children03 extends Parent03 {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Children{" +
                "password='" + password + '\'' +
                '}';
    }
}
