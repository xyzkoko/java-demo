package com.sirius.demo.domain;

import lombok.Data;

/**
 * 用户实体类
 * @author Sirius
 */
@Data
public class User {

    private int id;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
