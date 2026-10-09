package ucb.edu.bo.internship.internship_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ucb.edu.bo.internship.internship_backend.dto.EmailRequest;
import ucb.edu.bo.internship.internship_backend.dto.EmailRequestMassive;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@EnableAsync
public class EmailService {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private Executor taskExecutor;

    @Value("${email.service.url}")
    private String emailServiceUrl;

    public EmailService(RestTemplate restTemplate, @Qualifier("taskExecutor") Executor executor) {
        this.restTemplate = restTemplate;
        this.taskExecutor = executor;
    }

    @Async
    public CompletableFuture<String> enviarCorreo(EmailRequest emailRequest) {
        logger.debug("Enviando correo individual");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<EmailRequest> requestEntity = new HttpEntity<>(emailRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                emailServiceUrl + "/enviar-email",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            logger.info("Correo enviado con éxito: " + responseEntity.getBody());
            return CompletableFuture.completedFuture(responseEntity.getBody());
        } else {
            logger.error("Error enviando correo: " + responseEntity.getStatusCode());
            return CompletableFuture.completedFuture("Error: " + responseEntity.getStatusCode());
        }
    }

    @Async
    public CompletableFuture<String> enviarCorreoMasivo(EmailRequestMassive emailRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<EmailRequestMassive> requestEntity = new HttpEntity<>(emailRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                emailServiceUrl + "/enviar-email-masivo",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return CompletableFuture.completedFuture(responseEntity.getBody());
        } else {
            return CompletableFuture.completedFuture("Error: " + responseEntity.getStatusCode());
        }
    }

}
