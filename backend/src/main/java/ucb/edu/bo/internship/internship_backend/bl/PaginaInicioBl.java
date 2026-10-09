package ucb.edu.bo.internship.internship_backend.bl;

import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dto.RecuentoPaginaInicioDto;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;

import java.util.Arrays;

@Service
public class PaginaInicioBl {
    private final InstitucionesDao institucionesDao;
    private final UsuariosDao usuariosDao;
    private final PasantiasDao pasantiasDao;
    public PaginaInicioBl(InstitucionesDao institucionesDao, UsuariosDao usuariosDao, PasantiasDao pasantiasDao) {
        this.institucionesDao = institucionesDao;
        this.usuariosDao = usuariosDao;
        this.pasantiasDao = pasantiasDao;
    }
    //Obtener el recuento de instituciones, estudiantes y pasantias
    public RecuentoPaginaInicioDto obtenerRecuentoInstituciones(){
        try{
            return new RecuentoPaginaInicioDto(
                    institucionesDao.countAllByActivo(),
                    usuariosDao.countAllByActivoAndRolesIdrolesRolEqualsESTUDIANTE(),
                    pasantiasDao.countAllByActivoAndSinaplicantesIsNotTrue());
        }catch (Exception e){
            throw new InstitucionServiceExcepcion("Error al obtener el recuento",e);
        }
    }

}
