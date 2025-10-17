package com.cointracker.export;

import com.cointracker.model.Transaction;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {
    
    
    private static final String[] HEADER = {
        "Transaction Hash",
        "Date & Time",
        "From Address",
        "To Address",
        "Transaction Type",
        "Asset Contract Address",
        "Asset Symbol / Name",
        "Token ID",
        "Value / Amount",
        "Gas Fee (ETH)"
    };
    
    public void exportToCsv(List<Transaction> transactions, String filename) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            // Write header
            writer.writeNext(HEADER);
            
            // Write transaction rows
            for (Transaction transaction : transactions) {
                String[] row = transaction.toCsvRow();
                writer.writeNext(row);
            }
        }
        
        System.out.println("✅ Successfully exported " + transactions.size() + " transactions to: " + filename);
    }

    public String exportWithSummary(List<Transaction> transactions, String filename) throws IOException {
        exportToCsv(transactions, filename);
        
        // Calculate statistics
        long normalCount = transactions.stream()
                .filter(t -> t.getType().name().equals("ETH_TRANSFER"))
                .count();
        long internalCount = transactions.stream()
                .filter(t -> t.getType().name().equals("ETH_INTERNAL"))
                .count();
        long erc20Count = transactions.stream()
                .filter(t -> t.getType().name().equals("ERC20"))
                .count();
        long erc721Count = transactions.stream()
                .filter(t -> t.getType().name().equals("ERC721"))
                .count();
        
        return String.format(
                "\n📊 Export Summary:\n" +
                "   • Total Transactions: %d\n" +
                "   • Normal ETH Transfers: %d\n" +
                "   • Internal Transfers: %d\n" +
                "   • ERC-20 Transfers: %d\n" +
                "   • ERC-721 NFT Transfers: %d\n" +
                "   • Output File: %s",
                transactions.size(),
                normalCount,
                internalCount,
                erc20Count,
                erc721Count,
                filename
        );
    }
}
