package com.wty.joined_subclass_09;

public class Children02 extends Parent02 {

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
