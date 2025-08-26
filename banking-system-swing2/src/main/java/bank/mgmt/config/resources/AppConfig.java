package bank.mgmt.config.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Application configuration manager
 * Loads and manages application settings from properties file
 */
public class AppConfig {
    
    private static AppConfig instance;
    private Properties properties;
    
    private AppConfig() {
        loadProperties();
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    
    private void loadProperties() {
        properties = new Properties();
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("config/application.properties")) {
            
            if (is != null) {
                properties.load(is);
                System.out.println("✅ Configuration loaded successfully");
            } else {
                System.err.println("⚠️ Configuration file not found, using defaults");
                setDefaultProperties();
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to load configuration: " + e.getMessage());
            setDefaultProperties();
        }
    }
    
    private void setDefaultProperties() {
        properties.setProperty("api.base.url", "http://localhost:8080/api");
        properties.setProperty("api.timeout.connect", "30000");
        properties.setProperty("api.timeout.read", "30000");
        properties.setProperty("ui.theme", "FlatLaf");
    }
    
    public String getApiBaseUrl() {
        return properties.getProperty("api.base.url", "http://localhost:8080/api");
    }
    
    public int getConnectTimeout() {
        return Integer.parseInt(properties.getProperty("api.timeout.connect", "30000"));
    }
    
    public int getReadTimeout() {
        return Integer.parseInt(properties.getProperty("api.timeout.read", "30000"));
    }
    
    public String getUiTheme() {
        return properties.getProperty("ui.theme", "FlatLaf");
    }
    
    public boolean isDeveloperMode() {
        return Boolean.parseBoolean(properties.getProperty("dev.mode", "false"));
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
