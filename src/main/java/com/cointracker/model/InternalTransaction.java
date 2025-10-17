package com.cointracker.model;

/**
 * Represents an internal ETH transfer transaction.
 * These occur within smart contracts and are not directly initiated by users.
 */
public class InternalTransaction extends Transaction {

    public InternalTransaction() {
        super();
        this.type = TransactionType.ETH_INTERNAL;
        this.assetSymbol = "ETH";
        this.gasFee = null;
    }
    
    public InternalTransaction(String transactionHash, java.time.LocalDateTime dateTime,
                               String fromAddress, String toAddress,
                               java.math.BigDecimal value) {
        super();
        this.transactionHash = transactionHash;
        this.dateTime = dateTime;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.value = value;
        this.type = TransactionType.ETH_INTERNAL;
        this.assetSymbol = "ETH";
        this.gasFee = null; 
        this.assetContractAddress = "";
        this.tokenId = "";
    }

    @Override
    public String toString() {
        return "InternalTransaction{" +
                "hash='" + transactionHash + '\'' +
                ", from='" + fromAddress + '\'' +
                ", to='" + toAddress + '\'' +
                ", value=" + value + " ETH" +
                ", dateTime=" + dateTime +
                '}';
    }
}
