package com.cointracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

/**
 * Generic wrapper for Etherscan API responses.
 * 
 * <p>Etherscan API returns responses in this format:
 * <pre>
 * {
 *   "status": "1",           // "1" = success, "0" = error
 *   "message": "OK",         // Human-readable status
 *   "result": [...]          // Array of transaction data (or error message if status="0")
 * }
 * </pre>
 * 
 * <p>This class uses Jackson annotations for JSON deserialization and ignores
 * unknown properties to maintain forward compatibility with API changes.
 * 
 * @param <T> The type of data contained in the result field
 * 
 * @author CoinTracker Team
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {
    
    /**
     * API response status code.
     * "1" indicates success, "0" indicates error.
     */
    @JsonProperty("status")
    private final String status;
    
    /**
     * Human-readable status message.
     * Examples: "OK", "No transactions found", "NOTOK"
     */
    @JsonProperty("message")
    private final String message;
    
    /**
     * The actual response data.
     * Contains a list of transactions on success, or error details on failure.
     */
    @JsonProperty("result")
    private final T result;
    
    /**
     * Default constructor required by Jackson for deserialization.
     */
    public ApiResponse() {
        this.status = "";
        this.message = "";
        this.result = null;
    }
    
    /**
     * Constructs an ApiResponse with the specified values.
     * 
     * @param status  The API status code
     * @param message The status message
     * @param result  The response data
     */
    public ApiResponse(String status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
    
    /**
     * Checks if the API response indicates success.
     * 
     * @return true if status equals "1", false otherwise
     */
    public boolean isSuccess() {
        return "1".equals(status);
    }
    
    /**
     * Gets the status code.
     * 
     * @return The API status code
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Gets the status message.
     * 
     * @return The human-readable status message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Gets the result data.
     * 
     * @return The response data, or null if none exists
     */
    public T getResult() {
        return result;
    }
    
    /**
     * Gets the result as a list, or an empty list if result is null.
     * Useful for avoiding null pointer exceptions when processing results.
     * 
     * @return The result as a list, or empty list if null
     */
    @SuppressWarnings("unchecked")
    public List<T> getResultAsList() {
        if (result == null) {
            return Collections.emptyList();
        }
        if (result instanceof List) {
            return (List<T>) result;
        }
        return Collections.emptyList();
    }
    
    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
