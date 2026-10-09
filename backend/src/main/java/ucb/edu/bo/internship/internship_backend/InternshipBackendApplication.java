package ucb.edu.bo.internship.internship_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class InternshipBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternshipBackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer	(){
		return new WebMvcConfigurer(){
			@Override
				public void addCorsMappings(CorsRegistry registry){
					registry.addMapping("/**").allowedOrigins("*","http://localhost:5173","https://internship.sis-ucb.online")
					.allowedMethods("GET","POST","PUT","DELETE")
					.allowedHeaders("*");
			}
		};
	}

}
