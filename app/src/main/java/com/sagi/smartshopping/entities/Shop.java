package com.sagi.smartshopping.entities;

public class Shop {

    private String key;
    private String name;
    private String mallName;
    private String city;
    private String openTime;
    private String category;
    private int floor;
    private String phone;
    private String description;
    private String address;
    private String urlLogo;

    public Shop(String name, String mallName, String city, String urlLogo,String openTime, String category,
                int floor, String phone, String description, String address) {
        this.name = name;
        this.mallName = mallName;
        this.city = city;
        this.urlLogo = urlLogo;
        this.openTime = openTime;
        this.category = category;
        this.floor = floor;
        this.phone = phone;
        this.description = description;
        this.address = address;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
