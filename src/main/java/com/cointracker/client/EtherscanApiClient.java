package com.cointracker.client;

import com.cointracker.config.ApiConfig;
import com.cointracker.dto.ApiResponse;
import com.cointracker.dto.TransactionDto;
import com.cointracker.model.Transaction;
import com.cointracker.service.TransactionMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EtherscanApiClient implements BlockchainApiClient {
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final TransactionMapper mapper;
    private final ApiConfig config;
    
    public EtherscanApiClient(ApiConfig config) {
        this.config = config;
        this.mapper = new TransactionMapper();
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(config.getHttpConnectTimeout()))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }
    
    @Override
    public List<Transaction> fetchNormalTransactions(String address) throws IOException, InterruptedException {
        String[] endpoint = config.getEtherscanNormalTxEndpoint();
        return fetchAllPages(endpoint[0], endpoint[1], address, mapper::mapNormalTransaction);
    }
    
    @Override
    public List<Transaction> fetchInternalTransactions(String address) throws IOException, InterruptedException {
        String[] endpoint = config.getEtherscanInternalTxEndpoint();
        return fetchAllPages(endpoint[0], endpoint[1], address, mapper::mapInternalTransaction);
    }
    
    @Override
    public List<Transaction> fetchErc20Transactions(String address) throws IOException, InterruptedException {
        String[] endpoint = config.getEtherscanErc20TxEndpoint();
        return fetchAllPages(endpoint[0], endpoint[1], address, mapper::mapErc20Transaction);
    }
    
    @Override
    public List<Transaction> fetchErc721Transactions(String address) throws IOException, InterruptedException {
        String[] endpoint = config.getEtherscanErc721TxEndpoint();
        return fetchAllPages(endpoint[0], endpoint[1], address, mapper::mapErc721Transaction);
    }
    
    @Override
    public String getProviderName() {
        return "Etherscan";
    }
    
    private List<Transaction> fetchAllPages(String module, String action, String address, 
                                           Function<TransactionDto, Transaction> mapper) 
            throws IOException, InterruptedException {
        List<Transaction> allResults = new ArrayList<>();
        long startBlock = 0;
        long currentBlock = 99999999;
        int batchSize = config.getEtherscanBatchSize();
        
        while (true) {
            String url = buildUrlWithBlockRange(module, action, address, startBlock, currentBlock);
            ApiResponse<List<TransactionDto>> response = makeApiCall(url);
            
            if (!response.isSuccess()) {
                System.err.println("Warning: " + response.getMessage());
                break;
            }
            
            List<TransactionDto> dtoList = response.getResult();
            if (dtoList == null || dtoList.isEmpty()) {
                break;
            }
            
            List<Transaction> transactions = dtoList.stream()
                    .map(mapper)
                    .collect(Collectors.toList());
            allResults.addAll(transactions);
            
            System.out.println("      Fetched " + transactions.size() + " records (blocks " + startBlock + " to " + currentBlock + ")");
            
            if (dtoList.size() < batchSize) {
                break;
            }
            
            String lastBlockNumber = dtoList.get(dtoList.size() - 1).getBlockNumber();
            startBlock = Long.parseLong(lastBlockNumber) + 1;
            
            Thread.sleep(config.getRateLimitDelayBetweenCalls());
        }
        
        return allResults;
    }
    
    private String buildUrlWithBlockRange(String module, String action, String address, long startBlock, long endBlock) {
        return config.getEtherscanBaseUrl() + 
                "?chainid=" + config.getEtherscanChainId() +
                "&module=" + module +
                "&action=" + action +
                "&address=" + address +
                "&startblock=" + startBlock +
                "&endblock=" + endBlock +
                "&sort=asc" +
                "&apikey=" + config.getEtherscanApiKey();
    }
    
    private ApiResponse<List<TransactionDto>> makeApiCall(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(config.getHttpRequestTimeout()))
                .GET()
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new IOException("HTTP failed: " + response.statusCode());
        }
        
        TypeReference<ApiResponse<List<TransactionDto>>> typeRef = 
                new TypeReference<ApiResponse<List<TransactionDto>>>() {};
        return objectMapper.readValue(response.body(), typeRef);
    }
}
