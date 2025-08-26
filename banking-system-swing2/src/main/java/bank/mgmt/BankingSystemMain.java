package bank.mgmt;

import bank.mgmt.config.AppConfig;
import bank.mgmt.api.BankingApiClient;
import bank.mgmt.util.SessionManager;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

/**
 * Main application entry point
 * Initializes the banking system and shows login screen
 */
public class BankingSystemMain {
    
    public static void main(String[] args) {
        
        // Initialize application
        initializeApplication();
        
        // Check backend connectivity
        checkBackendConnection();
        
        // Start the UI
        startUserInterface();
    }
    
    /**
     * Initialize application configuration and services
     */
    private static void initializeApplication() {
        System.out.println("🏦 Banking System Starting...");
        System.out.println("📅 " + java.time.LocalDateTime.now());
        System.out.println("☕ Java Version: " + System.getProperty("java.version"));
        
        // Load configuration
        AppConfig config = AppConfig.getInstance();
        System.out.println("⚙️ Configuration loaded");
        
        // Initialize API client  
        BankingApiClient apiClient = BankingApiClient.getInstance();
        System.out.println("🌐 API Client initialized");
        
        // Initialize session manager
        SessionManager sessionManager = SessionManager.getInstance();
        System.out.println("👤 Session Manager initialized");
        
        // Set up look and feel
        setupLookAndFeel();
    }
    
    /**
     * Setup modern look and feel
     */
    private static void setupLookAndFeel() {
        try {
            // Try FlatLaf first (modern look)
            UIManager.setLookAndFeel(new FlatLightLaf());
            System.out.println("🎨 FlatLaf theme applied");
            
        } catch (Exception e) {
            try {
                // Fallback to system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                System.out.println("🎨 System look and feel applied");
            } catch (Exception ex) {
                System.err.println("⚠️ Could not set look and feel: " + ex.getMessage());
            }
        }
        
        // Set default font
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("defaultFont", defaultFont);
    }
    
    /**
     * Check backend server connectivity
     */
    private static void checkBackendConnection() {
        System.out.println("🔍 Checking backend connectivity...");
        
        BankingApiClient apiClient = BankingApiClient.getInstance();
        boolean connected = apiClient.testConnection();
        
        if (connected) {
            System.out.println("✅ Backend server is reachable");
        } else {
            System.err.println("⚠️ Backend server is not reachable");
            
            // Show warning dialog
            SwingUtilities.invokeLater(() -> {
                int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Cannot connect to backend server.\n\n" +
                    "Please ensure:\n" +
                    "1. Spring Boot backend is running on port 8080\n" +
                    "2. Database server is running\n" +
                    "3. Network connection is available\n\n" +
                    "Do you want to continue anyway?",
                    "Backend Connection Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (choice == JOptionPane.NO_OPTION) {
                    System.out.println("👋 Application cancelled by user");
                    System.exit(0);
                }
            });
        }
    }
    
    /**
     * Start the user interface
     */
    private static void startUserInterface() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Show splash screen
                JFrame splash = createSplashScreen();
                splash.setVisible(true);
                
                // Simulate initialization delay
                Timer timer = new Timer(2000, e -> {
                    splash.dispose();
                    
                    // Show login screen
                    System.out.println("🖥️ Starting user interface...");
                    new Login().setVisible(true);
                    System.out.println("✅ Banking System ready!");
                });
                timer.setRepeats(false);
                timer.start();
                
            } catch (Exception e) {
                System.err.println("❌ Failed to start UI: " + e.getMessage());
                e.printStackTrace();
                
                JOptionPane.showMessageDialog(
                    null,
                    "Failed to start application: " + e.getMessage(),
                    "Startup Error",
                    JOptionPane.ERROR_MESSAGE
                );
                
                System.exit(1);
            }
        });
    }
    
    /**
     * Create splash screen
     */
    private static JFrame createSplashScreen() {
        JFrame splash = new JFrame();
        splash.setTitle("Banking System");
        splash.setSize(500, 300);
        splash.setLocationRelativeTo(null);
        splash.setUndecorated(true);
        splash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create content panel
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        content.setBackground(Color.WHITE);
        
        // Title
        JLabel title = new JLabel("🏦 Banking Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        
        // Loading message
        JLabel loading = new JLabel("Loading...", JLabel.CENTER);
        loading.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loading.setForeground(Color.GRAY);
        
        // Progress bar
        JProgressBar progress = new JProgressBar();
        progress.setIndeterminate(true);
        progress.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        
        // Version info
        JLabel version = new JLabel("Version 2.0 - Modern Banking Solution", JLabel.CENTER);
        version.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        version.setForeground(Color.LIGHT_GRAY);
        
        content.add(title, BorderLayout.NORTH);
        content.add(loading, BorderLayout.CENTER);
        content.add(progress, BorderLayout.SOUTH);
        
        splash.add(content);
        
        return splash;
    }
}
