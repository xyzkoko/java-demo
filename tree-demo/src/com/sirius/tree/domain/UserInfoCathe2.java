package com.sirius.tree.domain;

import lombok.Data;

import java.util.*;

@Data
public class UserInfoCathe2 {

    private static Random random = new Random();
    private static Calendar calendar = Calendar.getInstance();

    private int id;

    private String name;

    private String sex;

    private Date birthday;

    private List<UserCodeCathe> userCodeCathe = new ArrayList<>();

    private List<UserTypeCathe> userTypeCathe = new ArrayList<>();

    public UserInfoCathe2(int i) {
        this.id = i;
        this.name = "姓名";
        this.sex = String.valueOf(random.nextInt(3));
        this.birthday = this.randomDate();
    }

    private Date randomDate() {
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        long startDateTime = calendar.getTime().getTime();
        long endDateTime = System.currentTimeMillis();
        long randomDateTime = (long)(Math.random() * (endDateTime - startDateTime + 1)) + startDateTime;
        return new Date(randomDateTime);
    }
}
