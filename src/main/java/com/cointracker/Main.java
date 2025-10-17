package com.cointracker;

import com.cointracker.client.BlockchainApiClient;
import com.cointracker.client.EtherscanApiClient;
import com.cointracker.config.ApiConfig;
import com.cointracker.export.CsvExporter;
import com.cointracker.model.Transaction;
import com.cointracker.service.TransactionService;

import java.util.List;

public class Main {
    
    private static final String ETHEREUM_ADDRESS_PATTERN = "^0x[0-9a-fA-F]{40}$";
    
    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception e) {
            System.err.println("\n❌ Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static String run(String[] args) throws Exception {
        printHeader();
        
        if (args.length == 0) {
            printUsage();
            throw new IllegalArgumentException("No Ethereum address provided");
        }
        
        String address = args[0];
        
        if (!address.matches(ETHEREUM_ADDRESS_PATTERN)) {
            System.err.println("❌ Error: Invalid Ethereum address format");
            System.err.println("   Expected: 0x followed by 40 hexadecimal characters");
            System.err.println("   Received: " + address);
            throw new IllegalArgumentException("Invalid Ethereum address format: " + address);
        }
        
        ApiConfig config = new ApiConfig();
        String provider = config.getApiProvider();
        System.out.println("API Provider: " + provider);
        
        BlockchainApiClient apiClient;
        switch (provider.toLowerCase()) {
            case "etherscan":
                apiClient = new EtherscanApiClient(config);
                break;
            default:
                System.err.println("❌ Error: Unsupported API provider: " + provider);
                System.err.println("   Supported providers: etherscan");
                throw new IllegalArgumentException("Unsupported API provider: " + provider);
        }
        
        System.out.println("Address: " + address);
        String apiKey = config.getEtherscanApiKey();
        System.out.println("API Key: " + maskApiKey(apiKey));
        System.out.println("=".repeat(80));
        System.out.println();
        
        TransactionService service = new TransactionService(apiClient, config);
        CsvExporter exporter = new CsvExporter();
        
        List<Transaction> transactions = service.getAllTransactions(address);
        
        System.out.println("\nExporting to CSV...");
        String summary = exporter.exportWithSummary(transactions, address);
        System.out.println(summary);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Transaction tracking complete!");
        System.out.println("=".repeat(80));
        
        return address + ".csv"; 
    }
    
    private static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Ethereum Transaction Tracker");
        System.out.println("=".repeat(80));
    }
    
    private static void printUsage() {
        System.err.println("\nUsage: java -jar eth-tracker.jar <ethereum_address>");
    }
    
    private static String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() < 8) {
            return "***";
        }
        return apiKey.substring(0, 8) + "*".repeat(24);
    }
}
