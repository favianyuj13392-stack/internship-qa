package ucb.edu.bo.internship.internship_backend.config;

import java.util.List;

public class SecurityCollection {
    private String name;
    private List<String> patterns;
    private List<String> methods;

    public SecurityCollection(String name, List<String> patterns, List<String> methods) {
        this.name = name;
        this.patterns = patterns;
        this.methods = methods;
    }

    public SecurityCollection() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public String toString() {
        return "SecurityCollection{" +
                "name=" + name +
                ", patterns=" + patterns +
                ", methods=" + methods +
                '}';
    }
}
