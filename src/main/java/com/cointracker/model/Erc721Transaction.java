package com.cointracker.model;

public class Erc721Transaction extends Transaction {

    private String tokenName;

    public Erc721Transaction() {
        super();
        this.type = TransactionType.ERC721;
        this.value = java.math.BigDecimal.ONE;
    }
    
    public Erc721Transaction(String transactionHash, java.time.LocalDateTime dateTime,
                            String fromAddress, String toAddress,
                            java.math.BigDecimal gasFee,
                            String contractAddress, String tokenSymbol, String tokenName,
                            String tokenId) {
        super();
        this.transactionHash = transactionHash;
        this.dateTime = dateTime;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.value = java.math.BigDecimal.ONE; 
        this.gasFee = gasFee;
        this.type = TransactionType.ERC721;
        this.assetContractAddress = contractAddress;
        this.assetSymbol = tokenSymbol;
        this.tokenName = tokenName;
        this.tokenId = tokenId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    @Override
    public String getAssetSymbol() {
        if (assetSymbol != null && tokenName != null) {
            return tokenName + " (" + assetSymbol + ")";
        }
        return assetSymbol != null ? assetSymbol : "";
    }

    @Override
    public String toString() {
        return "Erc721Transaction{" +
                "hash='" + transactionHash + '\'' +
                ", nft='" + assetSymbol + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", from='" + fromAddress + '\'' +
                ", to='" + toAddress + '\'' +
                ", gasFee=" + gasFee + " ETH" +
                ", dateTime=" + dateTime +
                '}';
    }
}
