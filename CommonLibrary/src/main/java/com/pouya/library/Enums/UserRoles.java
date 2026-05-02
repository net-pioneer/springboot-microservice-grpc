package com.pouya.library.Enums;

public enum UserRoles {
    USER(1),
    SUPPLIER(2),
    OBSERVER(3);
    private final int value;

    UserRoles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public String getAuthority() {
        return this.name(); // USER, SUPPLIER, ...
    }
}
