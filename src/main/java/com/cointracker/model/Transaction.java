package com.cointracker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class  {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected String transactionHash;
    protected LocalDateTime dateTime;
    protected String fromAddress;
    protected String toAddress;
    protected TransactionType type;
    protected String assetContractAddress;
    protected String assetSymbol;
    protected String tokenId;
    protected BigDecimal value;
    protected BigDecimal gasFee;

    // Constructor
    protected Transaction() {
    }

    // Getters and Setters
    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getAssetContractAddress() {
        return assetContractAddress != null ? assetContractAddress : "";
    }

    public void setAssetContractAddress(String assetContractAddress) {
        this.assetContractAddress = assetContractAddress;
    }

    public String getAssetSymbol() {
        return assetSymbol != null ? assetSymbol : "";
    }

    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }

    public String getTokenId() {
        return tokenId != null ? tokenId : "";
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getGasFee() {
        return gasFee;
    }

    public void setGasFee(BigDecimal gasFee) {
        this.gasFee = gasFee;
    }

    public String[] toCsvRow() {
        return new String[]{
            transactionHash,
            dateTime.format(DATE_TIME_FORMATTER),
            fromAddress,
            toAddress != null && !toAddress.isEmpty() ? toAddress : "(Contract Creation)",
            type.getDisplayName(),
            getAssetContractAddress(),
            getAssetSymbol(),
            getTokenId(),
            value != null ? value.toPlainString() : "0",
            gasFee != null ? gasFee.toPlainString() : ""
        };
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "hash='" + transactionHash + '\'' +
                ", type=" + type +
                ", from='" + fromAddress + '\'' +
                ", to='" + toAddress + '\'' +
                ", value=" + value +
                ", dateTime=" + dateTime +
                '}';
    }
}
