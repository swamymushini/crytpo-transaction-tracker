package com.cointracker.client;

import com.cointracker.model.Transaction;
import java.io.IOException;
import java.util.List;

public interface BlockchainApiClient {
    List<Transaction> fetchNormalTransactions(String address) throws IOException, InterruptedException;
    List<Transaction> fetchInternalTransactions(String address) throws IOException, InterruptedException;
    List<Transaction> fetchErc20Transactions(String address) throws IOException, InterruptedException;
    List<Transaction> fetchErc721Transactions(String address) throws IOException, InterruptedException;
    String getProviderName();
}
