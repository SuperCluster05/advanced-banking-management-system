package bank.mgmt.util;

import bank.mgmt.dto.UserInfo;
import bank.mgmt.api.BankingApiClient;

/**
 * Manages user session and authentication state
 */
public class SessionManager {
    
    private static SessionManager instance;
    
    private UserInfo currentUser;
    private String accessToken;
    private long loginTime;
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Set current user session
     */
    public void setSession(UserInfo user, String token) {
        this.currentUser = user;
        this.accessToken = token;
        this.loginTime = System.currentTimeMillis();
        
        // Set token in API client
        BankingApiClient.getInstance().setAuthToken(token);
        
        System.out.println("👤 Session established for: " + user.getFullName());
    }
    
    /**
     * Clear current session
     */
    public void clearSession() {
        System.out.println("🧹 Clearing session for: " + (currentUser != null ? currentUser.getFullName() : "unknown user"));
        
        this.currentUser = null;
        this.accessToken = null;
        this.loginTime = 0;
        
        // Clear token from API client
        BankingApiClient.getInstance().setAuthToken(null);
    }
    
    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null && accessToken != null;
    }
    
    /**
     * Check if session is expired (24 hours)
     */
    public boolean isSessionExpired() {
        if (!isLoggedIn()) return true;
        
        long sessionDuration = System.currentTimeMillis() - loginTime;
        long maxDuration = 24 * 60 * 60 * 1000; // 24 hours
        
        return sessionDuration > maxDuration;
    }
    
    /**
     * Get current user
     */
    public UserInfo getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Get access token
     */
    public String getAccessToken() {
        return accessToken;
    }
    
    /**
     * Get session duration in minutes
     */
    public long getSessionDurationMinutes() {
        if (!isLoggedIn()) return 0;
        return (System.currentTimeMillis() - loginTime) / (1000 * 60);
    }
}
