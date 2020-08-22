package com.example.ownercafeoda.MenuAdd;

public class MenuListItem {

    String menuName;
    String menuCountry;
    String menuPrice;

    public MenuListItem(String menuName, String menuCountry, String menuPrice) {
        this.menuName = menuName;
        this.menuCountry = menuCountry;
        this.menuPrice = menuPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuCountry() {
        return menuCountry;
    }

    public String getMenuPrice() {
        return menuPrice;
    }
}

