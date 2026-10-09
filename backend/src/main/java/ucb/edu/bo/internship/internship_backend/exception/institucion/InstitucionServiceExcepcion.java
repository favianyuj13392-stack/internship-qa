package ucb.edu.bo.internship.internship_backend.exception.institucion;

public class InstitucionServiceExcepcion extends RuntimeException{
    public InstitucionServiceExcepcion(String message, Throwable e) {
        super(message,e);
    }
}
