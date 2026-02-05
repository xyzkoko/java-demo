package com.sirius.tree.domain;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Data
public class UserInfoCathe {

    private static Random random = new Random();
    private static Calendar calendar = Calendar.getInstance();

    private int id;

    private String name;

    private String sex;

    private Date birthday;

    public UserInfoCathe(int i) {
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
