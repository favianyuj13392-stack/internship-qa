package ucb.edu.bo.internship.internship_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucb.edu.bo.internship.internship_backend.dao.CarrerasDao;
import ucb.edu.bo.internship.internship_backend.entity.Carreras;

import java.util.Arrays;

@Configuration
public class CarrerasConfig {
    @Bean
    public CommandLineRunner initDataCarreras(CarrerasDao carrerasDao){
        return (args) ->{
            if(carrerasDao.findAll().isEmpty()){
                carrerasDao.saveAll(Arrays.asList(
                    new Carreras(1, "ADMINISTRACIÓN DE EMPRESAS", "ADMINISTRACIÓN DE EMPRESAS"),
                    new Carreras(2, "ADMINISTRACIÓN TURÍSTICA", "ADMINISTRACIÓN TURÍSTICA"),
                    new Carreras(3, "ANTROPOLOGÍA", "ANTROPOLOGÍA"),
                    new Carreras(4, "ARQUITECTURA", "ARQUITECTURA"),
                    new Carreras(5, "ARQUITECTURA DE INTERIORES", "ARQUITECTURA DE INTERIORES"),
                    new Carreras(6, "CIENCIAS POLÍTICAS Y RELACIONES INTERNACIONALES", "CIENCIAS POLÍTICAS Y RELACIONES INTERNACIONALES"),
                    new Carreras(7, "COMUNICACIÓN MULTIMEDIA", "COMUNICACIÓN MULTIMEDIA"),
                    new Carreras(8, "COMUNICACIÓN SOCIAL", "COMUNICACIÓN SOCIAL"),
                    new Carreras(9, "CONTADURÍA PÚBLICA", "CONTADURÍA PÚBLICA"),
                    new Carreras(10, "CREACIÓN Y DESARROLLO DE EMPRESAS", "CREACIÓN Y DESARROLLO DE EMPRESAS"),
                    new Carreras(11, "DERECHO", "DERECHO"),
                    new Carreras(12, "DISEÑO DIGITAL", "DISEÑO DIGITAL"),
                    new Carreras(13, "DISEÑO GRÁFICO Y COMUNICACIÓN VISUAL", "DISEÑO GRÁFICO Y COMUNICACIÓN VISUAL"),
                    new Carreras(14, "ECONOMÍA", "ECONOMÍA"),
                    new Carreras(15, "ECONOMÍA E INTELIGENCIA DE NEGOCIOS", "ECONOMÍA E INTELIGENCIA DE NEGOCIOS"),
                    new Carreras(16, "ENFERMERÍA", "ENFERMERÍA"),
                    new Carreras(17, "FILOSOFÍA Y LETRAS", "FILOSOFÍA Y LETRAS"),
                    new Carreras(18, "GESTIÓN Y EMPRENDIMIENTO", "GESTIÓN Y EMPRENDIMIENTO"),
                    new Carreras(19, "INGENIERÍA AMBIENTAL", "INGENIERÍA AMBIENTAL"),
                    new Carreras(20, "INGENIERÍA BIOMÉDICA", "INGENIERÍA BIOMÉDICA"),
                    new Carreras(21, "INGENIERÍA BIOQUÍMICA Y DE BIOPROCESOS", "INGENIERÍA BIOQUÍMICA Y DE BIOPROCESOS"),
                    new Carreras(22, "INGENIERÍA CIVIL", "INGENIERÍA CIVIL"),
                    new Carreras(23, "INGENIERÍA COMERCIAL", "INGENIERÍA COMERCIAL"),
                    new Carreras(24, "INGENIERÍA DE AGRONEGOCIOS", "INGENIERÍA DE AGRONEGOCIOS"),
                    new Carreras(25, "INGENIERÍA DE SISTEMAS", "INGENIERÍA DE SISTEMAS"),
                    new Carreras(26, "INGENIERÍA DE TELECOMUNICACIONES", "INGENIERÍA DE TELECOMUNICACIONES"),
                    new Carreras(27, "INGENIERÍA EMPRESARIAL", "INGENIERÍA EMPRESARIAL"),
                    new Carreras(28, "INGENIERÍA EN BIOTECNOLOGÍA", "INGENIERÍA EN BIOTECNOLOGÍA"),
                    new Carreras(29, "INGENIERÍA EN ENERGÍA", "INGENIERÍA EN ENERGÍA"),
                    new Carreras(30, "INGENIERÍA EN INNOVACIÓN EMPRESARIAL", "INGENIERÍA EN INNOVACIÓN EMPRESARIAL"),
                    new Carreras(31, "INGENIERÍA EN INTERNET DE LAS COSAS", "INGENIERÍA EN INTERNET DE LAS COSAS"),
                    new Carreras(32, "INGENIERÍA EN LOGÍSTICA Y ANALÍTICA DE LA CADENA DE SUMINISTRO", "INGENIERÍA EN LOGÍSTICA Y ANALÍTICA DE LA CADENA DE SUMINISTRO"),
                    new Carreras(33, "INGENIERÍA EN MULTIMEDIA E INTERACTIVIDAD DIGITAL", "INGENIERÍA EN MULTIMEDIA E INTERACTIVIDAD DIGITAL"),
                    new Carreras(34, "INGENIERÍA FINANCIERA", "INGENIERÍA FINANCIERA"),
                    new Carreras(35, "INGENIERÍA INDUSTRIAL", "INGENIERÍA INDUSTRIAL"),
                    new Carreras(36, "INGENIERÍA MECATRÓNICA", "INGENIERÍA MECATRÓNICA"),
                    new Carreras(37, "INGENIERÍA QUÍMICA", "INGENIERÍA QUÍMICA"),
                    new Carreras(38, "KINESIOLOGÍA Y FISIOTERAPIA", "KINESIOLOGÍA Y FISIOTERAPIA"),
                    new Carreras(39, "MARKETING Y MEDIOS DIGITALES", "MARKETING Y MEDIOS DIGITALES"),
                    new Carreras(40, "MEDICINA", "MEDICINA"),
                    new Carreras(41, "NEGOCIOS INTERNACIONALES", "NEGOCIOS INTERNACIONALES"),
                    new Carreras(42, "NEGOCIOS Y CIENCIA DE DATOS", "NEGOCIOS Y CIENCIA DE DATOS"),
                    new Carreras(43, "NEGOCIOS Y DISEÑO", "NEGOCIOS Y DISEÑO"),
                    new Carreras(44, "NEGOCIOS Y TECNOLOGÍAS DE INFORMACIÓN", "NEGOCIOS Y TECNOLOGÍAS DE INFORMACIÓN"),
                    new Carreras(45, "ODONTOLOGÍA", "ODONTOLOGÍA"),
                    new Carreras(46, "PSICOLOGÍA", "PSICOLOGÍA"),
                    new Carreras(47, "PSICOPEDAGOGÍA", "PSICOPEDAGOGÍA")
                ));
            }
        };
    }
}
