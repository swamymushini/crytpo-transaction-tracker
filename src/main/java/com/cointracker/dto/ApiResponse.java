package com.cointracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {

	@JsonProperty("status")
	private final String status;
	@JsonProperty("message")
	private final String message;
	@JsonProperty("result")
	private final T result;

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

	public boolean isSuccess() {
		return "1".equals(status);
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public T getResult() {
		return result;
	}

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
		return "ApiResponse{" + "status='" + status + '\'' + ", message='" + message + '\'' + ", result=" + result
				+ '}';
	}
}
