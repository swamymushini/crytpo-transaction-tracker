package com.cointracker.service;

import com.cointracker.dto.TransactionDto;
import com.cointracker.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TransactionMapper {
    
    private static final BigDecimal WEI_PER_ETH = new BigDecimal("1000000000000000000");
    
   
    public NormalTransaction mapNormalTransaction(TransactionDto dto) {
        return new NormalTransaction(
                dto.getHash(),
                parseTimestamp(dto.getTimeStamp()),
                dto.getFrom(),
                dto.getTo(),
                weiToEth(dto.getValue()),
                calculateGasFee(dto.getGasUsed(), dto.getGasPrice())
        );
    }
    
   
    public InternalTransaction mapInternalTransaction(TransactionDto dto) {
        return new InternalTransaction(
                dto.getHash(),
                parseTimestamp(dto.getTimeStamp()),
                dto.getFrom(),
                dto.getTo(),
                weiToEth(dto.getValue())
        );
    }
    
    
    public Erc20Transaction mapErc20Transaction(TransactionDto dto) {
        int decimals = parseIntSafe(dto.getTokenDecimal(), 18);
        BigDecimal divisor = BigDecimal.TEN.pow(decimals);
        BigDecimal value = new BigDecimal(dto.getValue()).divide(divisor, decimals, RoundingMode.DOWN);
        
        return new Erc20Transaction(
                dto.getHash(),
                parseTimestamp(dto.getTimeStamp()),
                dto.getFrom(),
                dto.getTo(),
                value,
                calculateGasFee(dto.getGasUsed(), dto.getGasPrice()),
                dto.getContractAddress(),
                dto.getTokenSymbol(),
                dto.getTokenName(),
                decimals
        );
    }
    

    public Erc721Transaction mapErc721Transaction(TransactionDto dto) {
        return new Erc721Transaction(
                dto.getHash(),
                parseTimestamp(dto.getTimeStamp()),
                dto.getFrom(),
                dto.getTo(),
                calculateGasFee(dto.getGasUsed(), dto.getGasPrice()),
                dto.getContractAddress(),
                dto.getTokenSymbol(),
                dto.getTokenName(),
                dto.getTokenID()
        );
    }
    
    private BigDecimal weiToEth(String weiValue) {
        if (weiValue == null || weiValue.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        try {
            BigDecimal wei = new BigDecimal(weiValue);
            return wei.divide(WEI_PER_ETH, 18, RoundingMode.DOWN);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
    
    private BigDecimal calculateGasFee(String gasUsed, String gasPrice) {
        if (gasUsed == null || gasUsed.isEmpty() || gasPrice == null || gasPrice.isEmpty()) {
            return null;
        }
        
        try {
            BigDecimal used = new BigDecimal(gasUsed);
            BigDecimal price = new BigDecimal(gasPrice);
            BigDecimal totalWei = used.multiply(price);
            return totalWei.divide(WEI_PER_ETH, 18, RoundingMode.DOWN);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private LocalDateTime parseTimestamp(String timestamp) {
        if (timestamp == null || timestamp.isEmpty()) {
            return LocalDateTime.now();
        }
        
        try {
            long seconds = Long.parseLong(timestamp);
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.UTC);
        } catch (NumberFormatException e) {
            return LocalDateTime.now();
        }
    }
    
    private int parseIntSafe(String value, int defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
