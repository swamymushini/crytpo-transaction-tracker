package com.cointracker.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ApiConfig {
    
    private final Properties properties;
    
    public ApiConfig() {
        this.properties = new Properties();
        loadProperties();
    }
    
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("❌ application.properties not found in classpath!");
            }
            properties.load(input);
            resolveEnvironmentVariables();
        } catch (IOException e) {
            throw new RuntimeException("❌ Error loading application.properties: " + e.getMessage(), e);
        }
    }
    
    private void resolveEnvironmentVariables() {
        // Resolve ${ETHERSCAN_API_KEY:default} patterns
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (value.startsWith("${") && value.endsWith("}")) {
                String content = value.substring(2, value.length() - 1);
                String[] parts = content.split(":", 2);
                String envVar = parts[0];
                String defaultValue = parts.length > 1 ? parts[1] : "";
                
                String envValue = System.getenv(envVar);
                properties.setProperty(key, envValue != null ? envValue : defaultValue);
            }
        }
    }
    
    public String getApiProvider() {
        return properties.getProperty("api.provider", "etherscan");
    }
    
    public String getEtherscanBaseUrl() {
        return properties.getProperty("api.etherscan.base_url");
    }
    
    public int getEtherscanChainId() {
        return Integer.parseInt(properties.getProperty("api.etherscan.chain_id", "1"));
    }
    
    public String getEtherscanApiKey() {
        return properties.getProperty("api.etherscan.api_key");
    }
    
    public String[] getEtherscanNormalTxEndpoint() {
        String endpoint = properties.getProperty("api.etherscan.normal_transactions");
        String[] parts = endpoint.split("\\.");
        return parts.length == 2 ? parts : new String[]{"account", "txlist"};
    }
    
    public String[] getEtherscanInternalTxEndpoint() {
        String endpoint = properties.getProperty("api.etherscan.internal_transactions");
        String[] parts = endpoint.split("\\.");
        return parts.length == 2 ? parts : new String[]{"account", "txlistinternal"};
    }
    
    public String[] getEtherscanErc20TxEndpoint() {
        String endpoint = properties.getProperty("api.etherscan.erc20_transactions");
        String[] parts = endpoint.split("\\.");
        return parts.length == 2 ? parts : new String[]{"account", "tokentx"};
    }
    
    public String[] getEtherscanErc721TxEndpoint() {
        String endpoint = properties.getProperty("api.etherscan.erc721_transactions");
        String[] parts = endpoint.split("\\.");
        return parts.length == 2 ? parts : new String[]{"account", "tokennfttx"};
    }
    
    public int getEtherscanBatchSize() {
        return Integer.parseInt(properties.getProperty("api.etherscan.batch_size", "10000"));
    }
    
    public int getRateLimitDelayBetweenCalls() {
        return Integer.parseInt(properties.getProperty("api.rate_limit.delay_between_calls_ms", "500"));
    }
    
    public int getRateLimitDelayBetweenTypes() {
        return Integer.parseInt(properties.getProperty("api.rate_limit.delay_between_types_ms", "2000"));
    }
    
    public int getHttpConnectTimeout() {
        return Integer.parseInt(properties.getProperty("http.client.connect_timeout_seconds", "10"));
    }
    
    public int getHttpRequestTimeout() {
        return Integer.parseInt(properties.getProperty("http.client.request_timeout_seconds", "30"));
    }
    
    public String getAlchemyBaseUrl() {
        return properties.getProperty("api.alchemy.base_url");
    }
    
    public String getAlchemyApiKey() {
        return properties.getProperty("api.alchemy.api_key");
    }
    
    public String getInfuraBaseUrl() {
        return properties.getProperty("api.infura.base_url");
    }
    
    public String getInfuraApiKey() {
        return properties.getProperty("api.infura.api_key");
    }
}
