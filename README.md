# Java 25 Vulnerable String Manipulation Demo

⚠️ **WARNING: This project contains intentionally vulnerable code for educational purposes only. DO NOT use in production environments.**

## Overview

This project demonstrates various string manipulation vulnerabilities using Java 25 and Gradle 9.1.0. It includes dependencies with known security vulnerabilities to illustrate common attack vectors.

## Vulnerabilities Demonstrated

### 1. CVE-2022-42889: Apache Commons Text RCE
- **Library**: Apache Commons Text 1.1
- **Vulnerability**: Remote Code Execution through variable substitution
- **Impact**: Attackers can execute arbitrary code via `${script:javascript:...}` patterns

### 2. CVE-2017-12626: Jackson Deserialization RCE
- **Library**: Jackson Databind 2.8.8
- **Vulnerability**: Remote Code Execution through deserialization
- **Impact**: Deserializing untrusted JSON can lead to RCE

### 3. CVE-2021-44228: Log4Shell
- **Library**: Log4j 2.14.1
- **Vulnerability**: Remote Code Execution through JNDI lookups
- **Impact**: Logging user input without sanitization can trigger RCE via `${jndi:...}` patterns

### 4. CVE-2016-1000027: Spring Framework RCE
- **Library**: Spring Framework 4.3.30
- **Vulnerability**: Remote Code Execution through XML configuration
- **Impact**: Malicious XML configuration can lead to RCE

### 5. Apache Commons Lang Vulnerabilities
- **Library**: Apache Commons Lang 3.1
- **Vulnerability**: XSS and SQL injection through improper escaping
- **Impact**: Deprecated methods with known security issues

## Project Structure

```
├── build.gradle                    # Gradle build configuration
├── gradle/wrapper/                 # Gradle wrapper files
├── src/main/java/com/example/
│   └── VulnerableStringDemo.java  # Main demonstration class
└── src/test/java/com/example/
    └── VulnerableStringDemoTest.java # Test cases
```

## Dependencies

- **Java**: 25
- **Gradle**: 9.1.0
- **Apache Commons Text**: 1.1 (CVE-2022-42889)
- **Apache Commons Lang**: 3.1 (deprecated methods)
- **Jackson Databind**: 2.8.8 (CVE-2017-12626)
- **Log4j Core**: 2.14.1 (CVE-2021-44228)
- **Spring Framework**: 4.3.30 (CVE-2016-1000027)

## Building and Running

```bash
# Build the project
./gradlew build

# Run the vulnerable demo
./gradlew run

# Run tests
./gradlew test
```

## Security Notes

This project is designed for educational purposes to demonstrate:
- Common string manipulation vulnerabilities
- How vulnerable dependencies can be exploited
- The importance of input validation and sanitization
- Security best practices for string processing

## Mitigation Strategies

1. **Update Dependencies**: Use latest versions of all libraries
2. **Input Validation**: Always validate and sanitize user input
3. **Output Encoding**: Properly encode output to prevent XSS
4. **Logging Security**: Never log unsanitized user input
5. **Deserialization**: Use safe deserialization practices
6. **Configuration Security**: Validate XML/JSON configuration files

## Disclaimer

This code is for educational purposes only. The vulnerabilities demonstrated here should never be used in production environments. Always follow security best practices and keep dependencies updated.