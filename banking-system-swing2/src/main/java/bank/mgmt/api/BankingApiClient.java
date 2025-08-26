package bank.mgmt.api;

import bank.mgmt.config.AppConfig;
import bank.mgmt.dto.*;
import bank.mgmt.exception.ApiException;
import bank.mgmt.exception.AuthenticationException;
import bank.mgmt.exception.NetworkException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Complete Banking API Client for REST communication
 * Handles all HTTP communication with Spring Boot backend
 */
public class BankingApiClient {
    
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final AppConfig config;
    private String authToken;
    
    // Singleton instance
    private static BankingApiClient instance;
    
    private BankingApiClient() {
        this.config = AppConfig.getInstance();
        this.objectMapper = createObjectMapper();
        this.httpClient = createHttpClient();
        
        System.out.println("🔧 Banking API Client initialized");
        System.out.println("🌐 Base URL: " + config.getApiBaseUrl());
    }
    
    public static BankingApiClient getInstance() {
        if (instance == null) {
            synchronized (BankingApiClient.class) {
                if (instance == null) {
                    instance = new BankingApiClient();
                }
            }
        }
        return instance;
    }
    
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    
    private OkHttpClient createHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS);
        
        // Add logging in development mode
        if (config.isDeveloperMode()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        
        // Add auth token interceptor
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            
            if (authToken != null) {
                requestBuilder.header("Authorization", "Bearer " + authToken);
            }
            
            requestBuilder.header("Content-Type", "application/json");
            requestBuilder.header("Accept", "application/json");
            
            return chain.proceed(requestBuilder.build());
        });
        
        return builder.build();
    }
    
    /**
     * Set authentication token
     */
    public void setAuthToken(String token) {
        this.authToken = token;
        System.out.println("🔑 Auth token " + (token != null ? "set" : "cleared"));
    }
    
    /**
     * Test backend connectivity
     */
    public boolean testConnection() {
        try {
            Request request = new Request.Builder()
                    .url(config.getApiBaseUrl() + "/health")
                    .get()
                    .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                return response.isSuccessful() || response.code() == 401; // 401 means server is up but needs auth
            }
        } catch (Exception e) {
            System.err.println("🔴 Connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * User login
     */
    public LoginResponse login(String email, String password) throws ApiException {
        try {
            LoginRequest loginRequest = new LoginRequest(email, password);
            String json = objectMapper.writeValueAsString(loginRequest);
            
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(config.getApiBaseUrl() + "/auth/login")
                    .post(body)
                    .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = objectMapper.readValue(responseBody, LoginResponse.class);
                    this.authToken = loginResponse.getAccessToken();
                    System.out.println("✅ Login successful for: " + email);
                    return loginResponse;
                } else {
                    System.err.println("❌ Login failed: " + response.code() + " - " + responseBody);
                    throw new AuthenticationException("Login failed: " + responseBody);
                }
            }
            
        } catch (IOException e) {
            throw new NetworkException("Network error during login", e);
        } catch (Exception e) {
            throw new ApiException("Login failed", e);
        }
    }
    
    /**
     * Get user accounts
     */
    public AccountInfo[] getUserAccounts() throws ApiException {
        try {
            Request request = new Request.Builder()
                    .url(config.getApiBaseUrl() + "/accounts")
                    .get()
                    .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                
                if (response.isSuccessful()) {
                    return objectMapper.readValue(responseBody, AccountInfo[].class);
                } else {
                    throw new ApiException("Failed to get accounts: " + responseBody);
                }
            }
            
        } catch (IOException e) {
            throw new NetworkException("Network error getting accounts", e);
        }
    }
    
    /**
     * Get account balance
     */
    public BalanceInfo getAccountBalance(String accountNumber) throws ApiException {
        try {
            Request request = new Request.Builder()
                    .url(config.getApiBaseUrl() + "/accounts/number/" + accountNumber + "/balance")
                    .get()
                    .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                
                if (response.isSuccessful()) {
                    return objectMapper.readValue(responseBody, BalanceInfo.class);
                } else {
                    throw new ApiException("Failed to get balance: " + responseBody);
                }
            }
            
        } catch (IOException e) {
            throw new NetworkException("Network error getting balance", e);
        }
    }
    
    /**
     * Deposit money
     */
    public TransactionInfo deposit(String accountNumber, DepositRequest request) throws ApiException {
        try {
            String json = objectMapper.writeValueAsString(request);
            
            RequestBody body = RequestBody.create(json, JSON);
            Request httpRequest = new Request.Builder()
                    .url(config.getApiBaseUrl() + "/accounts/number/" + accountNumber + "/deposit")
                    .post(body)
                    .build();
            
            try (Response response = httpClient.newCall(httpRequest).execute()) {
                String responseBody = response.body().string();
                
                if (response.isSuccessful()) {
                    return objectMapper.readValue(responseBody, TransactionInfo.class);
                } else {
                    throw new ApiException("Deposit failed: " + responseBody);
                }
            }
            
        } catch (IOException e) {
            throw new NetworkException("Network error during deposit", e);
        }
    }
    
    /**
     * Withdraw money  
     */
    public TransactionInfo withdraw(String accountNumber, WithdrawRequest request) throws ApiException {
        try {
            String json = objectMapper.writeValueAsString(request);
            
            RequestBody body = RequestBody.create(json, JSON);
            Request httpRequest = new Request.Builder()
                    .url(config.getApiBaseUrl() + "/accounts/number/" + accountNumber + "/withdraw")
                    .post(body)
                    .build();
            
            try (Response response = httpClient.newCall(httpRequest).execute()) {
                String responseBody = response.body().string();
                
                if (response.isSuccessful()) {
                    return objectMapper.readValue(responseBody, TransactionInfo.class);
                } else {
                    throw new ApiException("Withdrawal failed: " + responseBody);
                }
            }
            
        } catch (IOException e) {
            throw new NetworkException("Network error during withdrawal", e);
        }
    }
    
    /**
     * Transfer money
     */
    public TransactionInfo transfer(TransferRequest request) throws ApiException {
        try {
            String json = objectMapper.writeValueAsString(request);
            
            RequestBody body = RequestBody.create(json, JSON);
            Request httpRequest = new Request.Builder()
                    .url(config.getApiBaseUrl() + "/accounts/transfer")
                    .post(body)
                    .build();
            
            try (Response response = httpClient.newCall(httpRequest).execute()) {
                String responseBody = response.body().string();
                
                if (response.isSuccessful()) {
                    return objectMapper.readValue(responseBody, TransactionInfo.class);
                } else {
                    throw new ApiException("Transfer failed: " + responseBody);
                }
            }
            
        } catch (IOException e) {
            throw new NetworkException("Network error during transfer", e);
        }
    }
    
    /**
     * Get transaction history
     */
    public TransactionInfo[] getTransactionHistory(String accountNumber, int page, int size) throws ApiException {
        try {
            String url = String.format("%s/accounts/number/%s/transactions?page=%d&size=%d",
                    config.getApiBaseUrl(), accountNumber, page, size);
            
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                
                if (response.isSuccessful()) {
                    // Handle paginated response
                    return objectMapper.readValue(responseBody, TransactionInfo[].class);
                } else {
                    throw new ApiException("Failed to get transaction history: " + responseBody);
                }
            }
            
        } catch (IOException e) {
            throw new NetworkException("Network error getting transaction history", e);
        }
    }
    
    /**
     * Logout user
     */
    public void logout() throws ApiException {
        try {
            if (authToken != null) {
                Request request = new Request.Builder()
                        .url(config.getApiBaseUrl() + "/auth/logout")
                        .post(RequestBody.create("", JSON))
                        .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    // Log result but don't throw exception
                    System.out.println(response.isSuccessful() ? "✅ Logout successful" : "⚠️ Logout response: " + response.code());
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Logout error: " + e.getMessage());
            // Don't throw exception for logout
        } finally {
            this.authToken = null;
        }
    }
}
