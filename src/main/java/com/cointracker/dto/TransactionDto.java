package com.cointracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {

	@JsonProperty("hash")
	private final String hash;

	@JsonProperty("blockNumber")
	private final String blockNumber;

	@JsonProperty("timeStamp")
	private final String timeStamp;

	@JsonProperty("from")
	private final String from;

	@JsonProperty("to")
	private final String to;

	@JsonProperty("value")
	private final String value;

	// ========== Gas-Related Fields (Normal & Internal Transactions) ==========

	/**
	 * Gas price in Wei. Example: "50000000000" = 50 Gwei
	 */
	@JsonProperty("gasPrice")
	private final String gasPrice;

	/**
	 * Gas units used by the transaction. Example: "21000" (standard ETH transfer)
	 */
	@JsonProperty("gasUsed")
	private final String gasUsed;

	/**
	 * Transaction confirmation status. "0" = failed, "1" = success (Only present in
	 * some response types)
	 */
	@JsonProperty("isError")
	private final String isError;

	// ========== Token-Specific Fields (ERC-20 & ERC-721) ==========

	/**
	 * Token contract address. Only present in tokentx and tokennfttx responses.
	 */
	@JsonProperty("contractAddress")
	private final String contractAddress;

	/**
	 * Token symbol (e.g., "USDC", "DAI", "POAP"). Only present in token/NFT
	 * transfers.
	 */
	@JsonProperty("tokenSymbol")
	private final String tokenSymbol;

	/**
	 * Token full name (e.g., "USD Coin", "Dai Stablecoin"). Only present in
	 * token/NFT transfers.
	 */
	@JsonProperty("tokenName")
	private final String tokenName;

	/**
	 * Token decimal places (e.g., "18" for most ERC-20 tokens). Used to convert raw
	 * value to human-readable format. Only present in ERC-20 transfers.
	 */
	@JsonProperty("tokenDecimal")
	private final String tokenDecimal;

	/**
	 * Unique token identifier for NFTs. Only present in ERC-721/ERC-1155 transfers.
	 * Example: "682" (POAP token ID)
	 */
	@JsonProperty("tokenID")
	private final String tokenID;

	/**
	 * Default constructor required by Jackson for deserialization.
	 */
	public TransactionDto() {
		this.hash = "";
		this.blockNumber = "";
		this.timeStamp = "";
		this.from = "";
		this.to = "";
		this.value = "";
		this.gasPrice = "";
		this.gasUsed = "";
		this.isError = "";
		this.contractAddress = "";
		this.tokenSymbol = "";
		this.tokenName = "";
		this.tokenDecimal = "";
		this.tokenID = "";
	}

	/**
	 * Full constructor for creating TransactionDto instances. Primarily used in
	 * testing.
	 */
	public TransactionDto(String hash, String blockNumber, String timeStamp, String from, String to, String value,
			String gasPrice, String gasUsed, String isError, String contractAddress, String tokenSymbol,
			String tokenName, String tokenDecimal, String tokenID) {
		this.hash = hash;
		this.blockNumber = blockNumber;
		this.timeStamp = timeStamp;
		this.from = from;
		this.to = to;
		this.value = value;
		this.gasPrice = gasPrice;
		this.gasUsed = gasUsed;
		this.isError = isError;
		this.contractAddress = contractAddress;
		this.tokenSymbol = tokenSymbol;
		this.tokenName = tokenName;
		this.tokenDecimal = tokenDecimal;
		this.tokenID = tokenID;
	}

	// ========== Getters ==========

	public String getHash() {
		return hash;
	}

	public String getBlockNumber() {
		return blockNumber;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getValue() {
		return value;
	}

	public String getGasPrice() {
		return gasPrice;
	}

	public String getGasUsed() {
		return gasUsed;
	}

	public String getIsError() {
		return isError;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public String getTokenSymbol() {
		return tokenSymbol;
	}

	public String getTokenName() {
		return tokenName;
	}

	public String getTokenDecimal() {
		return tokenDecimal;
	}

	public String getTokenID() {
		return tokenID;
	}

	/**
	 * Checks if this transaction represents a failed transaction.
	 * 
	 * @return true if isError equals "1", false otherwise
	 */
	public boolean isFailed() {
		return "1".equals(isError);
	}

	/**
	 * Checks if this DTO contains token-related data.
	 * 
	 * @return true if contractAddress is not null and not empty
	 */
	public boolean isTokenTransfer() {
		return contractAddress != null && !contractAddress.isEmpty();
	}

	@Override
	public String toString() {
		return "TransactionDto{" + "hash='" + hash + '\'' + ", timeStamp='" + timeStamp + '\'' + ", from='" + from
				+ '\'' + ", to='" + to + '\'' + ", value='" + value + '\'' + ", contractAddress='" + contractAddress
				+ '\'' + ", tokenSymbol='" + tokenSymbol + '\'' + ", tokenName='" + tokenName + '\'' + ", tokenID='"
				+ tokenID + '\'' + '}';
	}
}
