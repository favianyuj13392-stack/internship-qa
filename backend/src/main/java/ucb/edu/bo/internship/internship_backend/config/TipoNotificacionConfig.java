package ucb.edu.bo.internship.internship_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucb.edu.bo.internship.internship_backend.dao.TipoNotificacionDao;
import ucb.edu.bo.internship.internship_backend.entity.Tiponotificacion;

import java.util.Arrays;

@Configuration
public class TipoNotificacionConfig {
    @Bean
    public CommandLineRunner initDataTipoNotificacion(TipoNotificacionDao tipoNotificacionDao){
        return (args)->{
            if(tipoNotificacionDao.findAll().isEmpty()){
                tipoNotificacionDao.saveAll(Arrays.asList(
                        new Tiponotificacion(1, "Nueva pasantía"),
                        new Tiponotificacion(2, "Solicitud aceptada"),
                        new Tiponotificacion(3, "Solicitud rechazada"),
                        new Tiponotificacion(4, "Nuevo Postulante"),
                        new Tiponotificacion(5, "Nuevo perfil de empresa")
                ));
            }
        };
    }
}
