package com.example.ownercafeoda.OrderHistory;

public class OrderHistoryItemDTO {
    String ordid;
    String guestname;
    String guestphone;
    String ordertime;
    String menuElement;
    String icehot;
    String quantity;
    String price;
    String statusmsg;

    public OrderHistoryItemDTO(String guestname, String guestphone, String ordertime, String menuElement) {
        this.guestname = guestname;
        this.guestphone = guestphone;
        this.ordertime = ordertime;
        this.menuElement = menuElement;
    }

    public OrderHistoryItemDTO(String ordid, String guestphone, String ordertime, String menuElement, String icehot, String quantity, String price, String statusmsg) {
        this.ordid = ordid;
        this.guestphone = guestphone;
        this.ordertime = ordertime;
        this.menuElement = menuElement;
        this.icehot = icehot;
        this.quantity = quantity;
        this.price = price;
        this.statusmsg = statusmsg;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid;
    }

    public String getGuestname() {
        return guestname;
    }

    public String getGuestphone() {
        return guestphone;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public String getMenuElement() {
        return menuElement;
    }

    public String getIcehot() {
        return icehot;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public void setGuestphone(String guestphone) {
        this.guestphone = guestphone;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public void setMenuElement(String menuElement) {
        this.menuElement = menuElement;
    }

    public void setIcehot(String icehot) {
        this.icehot = icehot;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatusmsg() {
        return statusmsg;
    }

    public void setStatusmsg(String statusmsg) {
        this.statusmsg = statusmsg;
    }
}

