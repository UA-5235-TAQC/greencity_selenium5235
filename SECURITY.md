# Security Policy

## Supported Versions

This project is currently being maintained and security updates are provided for the latest version.

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Security Updates

### Recent Security Fixes

#### February 2026 Audit
- **TestNG Path Traversal Vulnerability (CVE)**
  - Updated TestNG from 7.4.0 to 7.10.2
  - Severity: HIGH
  - Status: FIXED ✅

## Reporting a Vulnerability

If you discover a security vulnerability in this project, please follow these steps:

1. **Do NOT** open a public issue
2. Contact the project maintainers privately via GitHub Security Advisory
3. Provide detailed information about the vulnerability:
   - Description of the issue
   - Steps to reproduce
   - Potential impact
   - Suggested fix (if any)

## Security Best Practices

When working with this project:

1. **Never commit sensitive data:**
   - API keys
   - Passwords
   - Tokens
   - Personal information
   - Use `config.properties.example` as a template

2. **Keep dependencies updated:**
   - Regularly check for security updates
   - Use `mvn versions:display-dependency-updates` to check for updates
   - Review GitHub Dependabot alerts

3. **Code Review:**
   - All code changes should be reviewed before merging
   - Pay special attention to:
     - User input handling
     - File operations
     - External API calls

4. **Testing:**
   - Run security scans regularly
   - Use CodeQL or similar tools for static analysis
   - Test with different user permissions

## Security Scanning

This project uses:
- **CodeQL** - Static code analysis for security vulnerabilities
- **GitHub Advisory Database** - Dependency vulnerability scanning
- **Maven Dependency Check** - Regular dependency audits

## Contact

For security concerns, please contact the repository maintainers through GitHub.

---

**Last Updated:** February 4, 2026  
**Next Review:** Quarterly or after significant dependency updates
