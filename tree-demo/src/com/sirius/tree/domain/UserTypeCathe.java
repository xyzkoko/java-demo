package com.sirius.tree.domain;

public class UserTypeCathe {

    private int id;
    private String type;

    public UserTypeCathe(int id) {
        this.id = id;
        this.type = String.valueOf((int)(Math.random() * (200 - 100 + 1)) + 100);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
