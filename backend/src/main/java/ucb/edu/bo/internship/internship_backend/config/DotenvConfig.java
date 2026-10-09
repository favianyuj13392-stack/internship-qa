package ucb.edu.bo.internship.internship_backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DotenvConfig {

    private final Logger logger = LoggerFactory.getLogger(DotenvConfig.class);

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure().load();
        Map<String, String> env = new HashMap<>();

        for(DotenvEntry entry : dotenv.entries()){
            env.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : env.entrySet()) {
            System.setProperty(entry.getKey(), entry.getValue());
        }
    }
}
