package com.sirius.tree.domain;

public class UserCodeCathe {

    private int id;
    private String code;

    public UserCodeCathe(int id) {
        this.id = id;
        this.code = String.valueOf((int)(Math.random() * (300 - 200 + 1)) + 200);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
