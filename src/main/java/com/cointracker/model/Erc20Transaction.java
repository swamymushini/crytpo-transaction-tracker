package com.cointracker.model;

/**
 * Represents an ERC-20 token transfer transaction.
 * ERC-20 is the standard for fungible tokens on Ethereum.
 */
public class Erc20Transaction extends Transaction {

    private String tokenName;
    private int tokenDecimal;

    public Erc20Transaction() {
        super();
        this.type = TransactionType.ERC20;
    }
    
    public Erc20Transaction(String transactionHash, java.time.LocalDateTime dateTime,
                           String fromAddress, String toAddress,
                           java.math.BigDecimal value, java.math.BigDecimal gasFee,
                           String contractAddress, String tokenSymbol, String tokenName,
                           int tokenDecimal) {
        super();
        this.transactionHash = transactionHash;
        this.dateTime = dateTime;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.value = value;
        this.gasFee = gasFee;
        this.type = TransactionType.ERC20;
        this.assetContractAddress = contractAddress;
        this.assetSymbol = tokenSymbol;
        this.tokenName = tokenName;
        this.tokenDecimal = tokenDecimal;
        this.tokenId = "";
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public int getTokenDecimal() {
        return tokenDecimal;
    }

    public void setTokenDecimal(int tokenDecimal) {
        this.tokenDecimal = tokenDecimal;
    }

    @Override
    public String getAssetSymbol() {
        if (assetSymbol != null && tokenName != null) {
            return assetSymbol + " (" + tokenName + ")";
        }
        return assetSymbol != null ? assetSymbol : "";
    }

    @Override
    public String toString() {
        return "Erc20Transaction{" +
                "hash='" + transactionHash + '\'' +
                ", token='" + assetSymbol + '\'' +
                ", from='" + fromAddress + '\'' +
                ", to='" + toAddress + '\'' +
                ", value=" + value +
                ", gasFee=" + gasFee + " ETH" +
                ", dateTime=" + dateTime +
                '}';
    }
}
