package com.sales.garage.entity;

public enum TransmissionType {
    MANUAL("Manual"),
    SEMI_AUTOMATIC("Semi Automatic"),
    AUTOMATIC("Automatic");

    private final String displayName;

    TransmissionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // You can add additional methods or properties as needed
}
