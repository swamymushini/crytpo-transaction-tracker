package com.cointracker.service;

import com.cointracker.client.BlockchainApiClient;
import com.cointracker.config.ApiConfig;
import com.cointracker.model.NormalTransaction;
import com.cointracker.model.Transaction;
import com.cointracker.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for TransactionService with mocked API client.
 */
public class TransactionServiceTest {
    
    private TransactionService service;
    private MockApiClient mockApiClient;
    private ApiConfig config;
    
    @BeforeEach
    void setUp() {
        config = new ApiConfig();
        mockApiClient = new MockApiClient();
        service = new TransactionService(mockApiClient, config);
    }
    
    @Test
    void testGetAllTransactions() throws IOException, InterruptedException {
        // Act
        List<Transaction> allTransactions = service.getAllTransactions("0xtest");
        
        // Assert
        assertNotNull(allTransactions, "Transaction list should not be null");
        assertEquals(4, allTransactions.size(), "Should have 4 transactions (1 of each type)");
        
        // Verify transactions are sorted by date
        for (int i = 0; i < allTransactions.size() - 1; i++) {
            assertTrue(
                allTransactions.get(i).getDateTime().isBefore(allTransactions.get(i + 1).getDateTime()) ||
                allTransactions.get(i).getDateTime().isEqual(allTransactions.get(i + 1).getDateTime()),
                "Transactions should be sorted by date"
            );
        }
        
        System.out.println("✅ Retrieved and sorted " + allTransactions.size() + " transactions");
    }
    
    @Test
    void testGetApiProviderName() {
        // Act
        String providerName = service.getApiProviderName();
        
        // Assert
        assertEquals("MockProvider", providerName, "Provider name should match mock");
    }
    
    /**
     * Mock implementation of BlockchainApiClient for testing.
     */
    private static class MockApiClient implements BlockchainApiClient {
        
        @Override
        public List<Transaction> fetchNormalTransactions(String address) {
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(new NormalTransaction(
                "0x123abc",
                LocalDateTime.of(2024, 1, 1, 10, 0),
                "0xfrom",
                "0xto",
                new java.math.BigDecimal("1.5"),
                new java.math.BigDecimal("0.001")
            ));
            return transactions;
        }
        
        @Override
        public List<Transaction> fetchInternalTransactions(String address) {
            List<Transaction> transactions = new ArrayList<>();
            NormalTransaction tx = new NormalTransaction(
                "0x456def",
                LocalDateTime.of(2024, 1, 2, 10, 0),
                "0xfrom",
                "0xto",
                new java.math.BigDecimal("0.5"),
                new java.math.BigDecimal("0.0")
            );
            tx.setType(TransactionType.ETH_INTERNAL);
            transactions.add(tx);
            return transactions;
        }
        
        @Override
        public List<Transaction> fetchErc20Transactions(String address) {
            List<Transaction> transactions = new ArrayList<>();
            NormalTransaction tx = new NormalTransaction(
                "0x789ghi",
                LocalDateTime.of(2024, 1, 3, 10, 0),
                "0xfrom",
                "0xto",
                new java.math.BigDecimal("100.0"),
                new java.math.BigDecimal("0.002")
            );
            tx.setType(TransactionType.ERC20);
            tx.setAssetSymbol("USDT (Tether USD)");
            tx.setAssetContractAddress("0xdac17f958d2ee523a2206206994597c13d831ec7");
            transactions.add(tx);
            return transactions;
        }
        
        @Override
        public List<Transaction> fetchErc721Transactions(String address) {
            List<Transaction> transactions = new ArrayList<>();
            NormalTransaction tx = new NormalTransaction(
                "0xabcxyz",
                LocalDateTime.of(2024, 1, 4, 10, 0),
                "0xfrom",
                "0xto",
                new java.math.BigDecimal("1"),
                new java.math.BigDecimal("0.003")
            );
            tx.setType(TransactionType.ERC721);
            tx.setAssetSymbol("POAP");
            tx.setAssetContractAddress("0x22c1f6050e56d2876009903609a2cc3fef83b415");
            tx.setTokenId("12345");
            transactions.add(tx);
            return transactions;
        }
        
        @Override
        public String getProviderName() {
            return "MockProvider";
        }
    }
}
