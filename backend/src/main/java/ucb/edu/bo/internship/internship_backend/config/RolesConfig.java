package ucb.edu.bo.internship.internship_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucb.edu.bo.internship.internship_backend.dao.RolesDao;
import ucb.edu.bo.internship.internship_backend.entity.Roles;

import java.util.Arrays;

@Configuration
public class RolesConfig {
    @Bean
    public CommandLineRunner initDataRoles(RolesDao rolesDao){
        return (args)->{
            if(rolesDao.findAll().isEmpty()){
                rolesDao.saveAll(Arrays.asList(
                    new Roles(1, "ESTUDIANTE", "ESTUDIANTE"),
                    new Roles(2, "ADMIN", "ADMIN"),
                    new Roles(3, "EMPRESA", "EMPRESA")
                ));
            }
        };
    }
}
