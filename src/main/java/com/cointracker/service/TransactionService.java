package com.cointracker.service;

import com.cointracker.client.BlockchainApiClient;
import com.cointracker.config.ApiConfig;
import com.cointracker.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionService {
    
    private final BlockchainApiClient apiClient;
    private final ApiConfig config;
    
    public TransactionService(BlockchainApiClient apiClient, ApiConfig config) {
        this.apiClient = apiClient;
        this.config = config;
    }
    
    public List<Transaction> getAllTransactions(String address) throws IOException, InterruptedException {
        List<Transaction> allTransactions = new ArrayList<>();
        
        System.out.println("Fetching normal transactions...");
        List<Transaction> normalTxs = apiClient.fetchNormalTransactions(address);
        allTransactions.addAll(normalTxs);
        System.out.println("   Found " + normalTxs.size() + " normal transactions");
        System.out.println("   Cooling down for " + config.getRateLimitDelayBetweenTypes() + "ms...\n");
        Thread.sleep(config.getRateLimitDelayBetweenTypes());
        
        System.out.println("Fetching internal transactions...");
        List<Transaction> internalTxs = apiClient.fetchInternalTransactions(address);
        allTransactions.addAll(internalTxs);
        System.out.println("   Found " + internalTxs.size() + " internal transactions");
        System.out.println("   Cooling down for " + config.getRateLimitDelayBetweenTypes() + "ms...\n");
        Thread.sleep(config.getRateLimitDelayBetweenTypes());
        
        System.out.println("Fetching ERC-20 token transfers...");
        List<Transaction> erc20Txs = apiClient.fetchErc20Transactions(address);
        allTransactions.addAll(erc20Txs);
        System.out.println("   Found " + erc20Txs.size() + " ERC-20 transfers");
        System.out.println("   Cooling down for " + config.getRateLimitDelayBetweenTypes() + "ms...\n");
        Thread.sleep(config.getRateLimitDelayBetweenTypes());
        
        System.out.println("Fetching ERC-721 NFT transfers...");
        List<Transaction> erc721Txs = apiClient.fetchErc721Transactions(address);
        allTransactions.addAll(erc721Txs);
        System.out.println("   Found " + erc721Txs.size() + " ERC-721 transfers");
        
        allTransactions.sort(Comparator.comparing(Transaction::getDateTime));
        return allTransactions;
    }
    
    public String getApiProviderName() {
        return apiClient.getProviderName();
    }
}
