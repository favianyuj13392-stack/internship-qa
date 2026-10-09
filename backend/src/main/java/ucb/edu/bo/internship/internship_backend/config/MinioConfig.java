package ucb.edu.bo.internship.internship_backend.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String url;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    @Primary
    public MinioClient minioClient(){
        return new MinioClient
                .Builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }

}
