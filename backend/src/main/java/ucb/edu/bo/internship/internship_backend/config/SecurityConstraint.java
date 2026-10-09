package ucb.edu.bo.internship.internship_backend.config;

import java.util.List;

public class SecurityConstraint {
    private List<String> authRoles;
    private List<SecurityCollection> securityCollections;

    public SecurityConstraint(List<String> authRoles, List<SecurityCollection> securityCollections) {
        this.authRoles = authRoles;
        this.securityCollections = securityCollections;
    }

    public SecurityConstraint() {
    }

    public List<String> getAuthRoles() {
        return authRoles;
    }

    public void setAuthRoles(List<String> authRoles) {
        this.authRoles = authRoles;
    }

    public List<SecurityCollection> getSecurityCollections() {
        return securityCollections;
    }

    public void setSecurityCollections(List<SecurityCollection> securityCollections) {
        this.securityCollections = securityCollections;
    }

    public String toString() {
        return "SecurityConstraint{" +
                "authRoles=" + authRoles +
                ", securityCollections=" + securityCollections +
                '}';
    }
}
