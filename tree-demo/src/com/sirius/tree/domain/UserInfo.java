package com.sirius.tree.domain;

import com.sirius.tree.App;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserInfo {

    private int id;

    private String name;

    private String sex;

    private Date birthday;

    private List<String> typeList = new ArrayList<>();

    private List<String> codeList = new ArrayList<>();

    public UserInfo(UserInfoCathe userInfoCathe) {
        this.id = userInfoCathe.getId();
        this.name = userInfoCathe.getName();
        this.sex = userInfoCathe.getSex();
        this.birthday = userInfoCathe.getBirthday();
        // TODO 耗时长!!!
        this.typeList = App.userTypeList.parallelStream().filter(c -> c.getId() == this.id).map(UserTypeCathe::getType).collect(Collectors.toList());
        this.codeList = App.userCodeList.parallelStream().filter(c -> c.getId() == this.id).map(UserCodeCathe::getCode).collect(Collectors.toList());
    }

    public UserInfo(UserInfoCathe2 userInfoCathe) {
        this.id = userInfoCathe.getId();
        this.name = userInfoCathe.getName();
        this.sex = userInfoCathe.getSex();
        this.birthday = userInfoCathe.getBirthday();
        this.typeList = userInfoCathe.getUserTypeCathe().stream().map(UserTypeCathe::getType).collect(Collectors.toList());
        this.codeList = userInfoCathe.getUserCodeCathe().stream().map(UserCodeCathe::getCode).collect(Collectors.toList());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", typeList=" + typeList +
                ", codeList=" + codeList +
                '}';
    }
}
