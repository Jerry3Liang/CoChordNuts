package com.ispan.recordshop.cochordnuts.dto;

import java.util.List;

public class MemberDTO {
    private String name;
    private String birthday;
    private String password;
    private String phone;
    private String email;
    private String address;
    private List<Integer> favoriteIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Integer> getFavoriteIds() {
        return favoriteIds;
    }

    public void setFavoriteIds(List<Integer> favoriteIds) {
        this.favoriteIds = favoriteIds;
    }

}
