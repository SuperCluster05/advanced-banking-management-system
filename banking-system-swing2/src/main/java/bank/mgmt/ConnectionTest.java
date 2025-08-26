package bank.mgmt.util;

import bank.mgmt.api.BankingApiClient;
import bank.mgmt.config.resources;

/**
 * Test connectivity and configuration
 */
public class ConnectionTest {
    
    public static void main(String[] args) {
        System.out.println("🧪 Banking System Connection Test");
        System.out.println("================================");
        
        try {
            // Test configuration loading
            AppConfig config = AppConfig.getInstance();
            System.out.println("✅ Configuration loaded");
            System.out.println("🌐 API Base URL: " + config.getApiBaseUrl());
            
            // Test API client creation
            BankingApiClient apiClient = BankingApiClient.getInstance();
            System.out.println("✅ API Client created");
            
            // Test backend connectivity
            System.out.println("🔍 Testing backend connection...");
            boolean connected = apiClient.testConnection();
            
            if (connected) {
                System.out.println("✅ Backend is reachable!");
                System.out.println("🎉 All tests passed!");
            } else {
                System.out.println("❌ Backend is not reachable");
                System.out.println("💡 Make sure Spring Boot backend is running on port 8080");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
