package com.cointracker.model;

public enum TransactionType {
    ETH_TRANSFER("ETH Transfer"),
    ETH_INTERNAL("ETH Internal"),
    ERC20("ERC-20"),
    ERC721("ERC-721");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
