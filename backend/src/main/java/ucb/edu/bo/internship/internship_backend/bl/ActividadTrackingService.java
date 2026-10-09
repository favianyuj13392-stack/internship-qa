package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucb.edu.bo.internship.internship_backend.dao.EventoAccesoDao;
import ucb.edu.bo.internship.internship_backend.dao.PadronEstudianteDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dao.VistaPasantiaDao;
import ucb.edu.bo.internship.internship_backend.entity.EventoAcceso;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;
import ucb.edu.bo.internship.internship_backend.entity.VistaPasantia;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActividadTrackingService {

    private static final Logger logger = LoggerFactory.getLogger(ActividadTrackingService.class);

    private final PadronEstudianteDao padronEstudianteDao;
    private final EventoAccesoDao eventoAccesoDao;
    private final VistaPasantiaDao vistaPasantiaDao;
    private final PasantiasDao pasantiasDao;
    private final UsuariosDao usuariosDao;

    public ActividadTrackingService(PadronEstudianteDao padronEstudianteDao,
                                    EventoAccesoDao eventoAccesoDao,
                                    VistaPasantiaDao vistaPasantiaDao,
                                    PasantiasDao pasantiasDao,
                                    UsuariosDao usuariosDao) {
        this.padronEstudianteDao = padronEstudianteDao;
        this.eventoAccesoDao = eventoAccesoDao;
        this.vistaPasantiaDao = vistaPasantiaDao;
        this.pasantiasDao = pasantiasDao;
        this.usuariosDao = usuariosDao;
    }

    @Transactional
    public void registrarAcceso(String correo, String kcUuid, String origen, String ip) {
        if (correo == null && kcUuid == null) {
            return;
        }

        Optional<PadronEstudiante> padronOpt = Optional.empty();
        if (correo != null) {
            padronOpt = padronEstudianteDao.findByCorreoIgnoreCase(correo.trim().toLowerCase());
        }
        if (padronOpt.isEmpty() && kcUuid != null) {
            padronOpt = padronEstudianteDao.findByKcUuid(kcUuid);
        }

        if (padronOpt.isEmpty()) {
            return;
        }

        PadronEstudiante estudiante = padronOpt.get();
        Date now = new Date();

        // Avoid repeated access events within a 4-hour window
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.HOUR_OF_DAY, -4);
        Date cuatroHorasAtras = cal.getTime();

        List<EventoAcceso> recientes = eventoAccesoDao.findRecientesPorEstudiante(estudiante, cuatroHorasAtras);
        if (recientes.isEmpty()) {
            EventoAcceso evento = new EventoAcceso(estudiante, now, origen != null ? origen : "PORTAL", ip);
            eventoAccesoDao.save(evento);
            logger.info("Access event recorded for student: {}", estudiante.getCorreo());
        }

        if (estudiante.getPrimerAcceso() == null) {
            estudiante.setPrimerAcceso(now);
        }
        estudiante.setUltimoAcceso(now);

        if (estudiante.getKcUuid() == null && kcUuid != null) {
            estudiante.setKcUuid(kcUuid);
        }

        if (estudiante.getUsuariosIdusuarios() == null && kcUuid != null) {
            Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
            if (usuario != null) {
                estudiante.setUsuariosIdusuarios(usuario);
            }
        }

        padronEstudianteDao.save(estudiante);
    }

    @Transactional
    public void registrarVistaPasantia(String correoOrUuid, Integer idPasantia, String ref) {
        if (correoOrUuid == null || idPasantia == null) {
            return;
        }

        Optional<PadronEstudiante> padronOpt = Optional.empty();
        if (correoOrUuid.contains("@")) {
            padronOpt = padronEstudianteDao.findByCorreoIgnoreCase(correoOrUuid.trim().toLowerCase());
        } else {
            padronOpt = padronEstudianteDao.findByKcUuid(correoOrUuid);
        }

        if (padronOpt.isEmpty()) {
            Usuarios u = usuariosDao.findByKcUuid(correoOrUuid);
            if (u != null && u.getCorreo() != null) {
                padronOpt = padronEstudianteDao.findByCorreoIgnoreCase(u.getCorreo().trim().toLowerCase());
            }
        }

        if (padronOpt.isEmpty()) {
            return;
        }

        PadronEstudiante estudiante = padronOpt.get();
        Optional<Pasantias> pasantiaOpt = pasantiasDao.findById(idPasantia);
        if (pasantiaOpt.isEmpty()) {
            return;
        }

        Pasantias pasantia = pasantiaOpt.get();
        Date now = new Date();

        String origen = "WEB";
        if (ref != null && (ref.equalsIgnoreCase("mail") || ref.equalsIgnoreCase("correo") || ref.equalsIgnoreCase("email"))) {
            origen = "CORREO";
        }

        Optional<VistaPasantia> vistaOpt = vistaPasantiaDao.findByPadronEstudianteAndPasantia(estudiante, pasantia);
        if (vistaOpt.isPresent()) {
            VistaPasantia vista = vistaOpt.get();
            vista.setVeces(vista.getVeces() != null ? vista.getVeces() + 1 : 1);
            vista.setUltimaVista(now);
            vistaPasantiaDao.save(vista);
        } else {
            VistaPasantia nuevaVista = new VistaPasantia(estudiante, pasantia, now, origen);
            vistaPasantiaDao.save(nuevaVista);
            logger.info("New internship view recorded for student {} on internship {}", estudiante.getCorreo(), idPasantia);
        }
    }
}
