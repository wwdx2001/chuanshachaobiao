package com.sh3h.datautil.data.entity;


public class DUAdjustingCard implements IDUEntity {
    private boolean isChecked;
    private int orderNumber;
    private String cardId;

    public DUAdjustingCard(boolean isChecked, int orderNumber, String cardId) {
        this.isChecked = isChecked;
        this.orderNumber = orderNumber;
        this.cardId = cardId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
