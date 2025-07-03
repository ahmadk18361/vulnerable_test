// VulnerableApp.java
public class VulnerableApp {
    public static void main(String[] args) {
        // Hardcoded password vulnerability
        String password = "admin123";  // SonarQube will flag this
        
        // SQL injection vulnerability
        String userInput = args[0];
        String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
        System.out.println("Executing: " + query);
    }
}
