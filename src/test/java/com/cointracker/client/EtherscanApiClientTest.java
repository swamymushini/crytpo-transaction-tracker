package com.cointracker.client;

import com.cointracker.config.ApiConfig;
import com.cointracker.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for EtherscanApiClient.
 * Tests with real Ethereum address: 0xa39b189482f984388a34460636fea9eb181ad1a6
 */
public class EtherscanApiClientTest {
    
    private EtherscanApiClient apiClient;
    private static final String TEST_ADDRESS = "0xa39b189482f984388a34460636fea9eb181ad1a6";
    
    @BeforeEach
    void setUp() {
        ApiConfig config = new ApiConfig();
        apiClient = new EtherscanApiClient(config);
    }
    
    @Test
    void testFetchNormalTransactions() throws IOException, InterruptedException {
        // Act
        List<Transaction> transactions = apiClient.fetchNormalTransactions(TEST_ADDRESS);
        
        // Assert
        assertNotNull(transactions, "Transaction list should not be null");
        assertFalse(transactions.isEmpty(), "Should have at least one normal transaction");
        
        // Verify first transaction structure
        Transaction firstTx = transactions.get(0);
        assertNotNull(firstTx.getTransactionHash(), "Transaction hash should not be null");
        assertNotNull(firstTx.getDateTime(), "Date time should not be null");
        assertNotNull(firstTx.getFromAddress(), "From address should not be null");
        assertNotNull(firstTx.getToAddress(), "To address should not be null");
        assertNotNull(firstTx.getValue(), "Value should not be null");
        
        System.out.println("✅ Fetched " + transactions.size() + " normal transactions");
        System.out.println("✅ First transaction hash: " + firstTx.getTransactionHash());
    }
    
    @Test
    void testFetchInternalTransactions() throws IOException, InterruptedException {
        // Act
        List<Transaction> transactions = apiClient.fetchInternalTransactions(TEST_ADDRESS);
        
        // Assert
        assertNotNull(transactions, "Transaction list should not be null");
        // Note: This address might have 0 internal transactions
        
        System.out.println("✅ Fetched " + transactions.size() + " internal transactions");
    }
    
    @Test
    void testFetchErc20Transactions() throws IOException, InterruptedException {
        // Act
        List<Transaction> transactions = apiClient.fetchErc20Transactions(TEST_ADDRESS);
        
        // Assert
        assertNotNull(transactions, "Transaction list should not be null");
        assertFalse(transactions.isEmpty(), "Should have at least one ERC-20 transaction");
        
        // Verify first ERC-20 transaction has token info
        Transaction firstTx = transactions.get(0);
        assertNotNull(firstTx.getAssetSymbol(), "Asset symbol should not be null for ERC-20");
        assertNotNull(firstTx.getAssetContractAddress(), "Contract address should not be null for ERC-20");
        
        System.out.println("✅ Fetched " + transactions.size() + " ERC-20 transactions");
        System.out.println("✅ First token: " + firstTx.getAssetSymbol());
    }
    
    @Test
    void testFetchErc721Transactions() throws IOException, InterruptedException {
        // Act
        List<Transaction> transactions = apiClient.fetchErc721Transactions(TEST_ADDRESS);
        
        // Assert
        assertNotNull(transactions, "Transaction list should not be null");
        // Note: This address might have 0 ERC-721 transactions
        
        System.out.println("✅ Fetched " + transactions.size() + " ERC-721 transactions");
    }
    
    @Test
    void testProviderName() {
        // Act
        String providerName = apiClient.getProviderName();
        
        // Assert
        assertEquals("Etherscan", providerName, "Provider name should be Etherscan");
    }
}
