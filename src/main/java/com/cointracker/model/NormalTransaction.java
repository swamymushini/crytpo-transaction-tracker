package com.cointracker.model;

public class NormalTransaction extends Transaction {

    public NormalTransaction() {
        super();
        this.type = TransactionType.ETH_TRANSFER;
        this.assetSymbol = "ETH";
    }
    
    public NormalTransaction(String transactionHash, java.time.LocalDateTime dateTime,
                            String fromAddress, String toAddress,
                            java.math.BigDecimal value, java.math.BigDecimal gasFee) {
        super();
        this.transactionHash = transactionHash;
        this.dateTime = dateTime;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.value = value;
        this.gasFee = gasFee;
        this.type = TransactionType.ETH_TRANSFER;
        this.assetSymbol = "ETH";
        this.assetContractAddress = "";
        this.tokenId = "";
    }

    @Override
    public String toString() {
        return "NormalTransaction{" +
                "hash='" + transactionHash + '\'' +
                ", from='" + fromAddress + '\'' +
                ", to='" + toAddress + '\'' +
                ", value=" + value + " ETH" +
                ", gasFee=" + gasFee + " ETH" +
                ", dateTime=" + dateTime +
                '}';
    }
}
