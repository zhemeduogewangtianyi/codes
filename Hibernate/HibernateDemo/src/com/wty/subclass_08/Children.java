package com.wty.subclass_08;

public class Children extends Parent{

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
