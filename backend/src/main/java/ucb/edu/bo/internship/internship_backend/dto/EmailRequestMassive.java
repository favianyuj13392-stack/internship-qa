package ucb.edu.bo.internship.internship_backend.dto;

import java.util.List;

public class EmailRequestMassive {
    private List<String> to;
    private String subject;
    private String body;

    public EmailRequestMassive() {
    }

    public EmailRequestMassive(List<String> to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
