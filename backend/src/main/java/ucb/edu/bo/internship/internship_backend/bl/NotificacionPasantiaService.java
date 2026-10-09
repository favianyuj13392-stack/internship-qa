package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import ucb.edu.bo.internship.internship_backend.dao.EnvioCorreoPasantiaDao;
import ucb.edu.bo.internship.internship_backend.dao.PadronEstudianteDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dto.EmailRequest;
import ucb.edu.bo.internship.internship_backend.entity.EnvioCorreoPasantia;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.Pasantiascarreras;
import ucb.edu.bo.internship.internship_backend.service.EmailService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificacionPasantiaService {

    private static final Logger logger = LoggerFactory.getLogger(NotificacionPasantiaService.class);

    private final PasantiasDao pasantiasDao;
    private final PadronEstudianteDao padronEstudianteDao;
    private final EnvioCorreoPasantiaDao envioCorreoPasantiaDao;
    private final EmailService emailService;

    @Value("${environment.frontend_url:http://localhost:5173}")
    private String frontendUrl;

    public NotificacionPasantiaService(PasantiasDao pasantiasDao,
                                       PadronEstudianteDao padronEstudianteDao,
                                       EnvioCorreoPasantiaDao envioCorreoPasantiaDao,
                                       EmailService emailService) {
        this.pasantiasDao = pasantiasDao;
        this.padronEstudianteDao = padronEstudianteDao;
        this.envioCorreoPasantiaDao = envioCorreoPasantiaDao;
        this.emailService = emailService;
    }

    @Async
    @Transactional
    public CompletableFuture<Integer> notificarEstudiantesPorCarrera(Integer idPasantias) {
        Optional<Pasantias> pasantiaOpt = pasantiasDao.findById(idPasantias);
        if (pasantiaOpt.isEmpty()) {
            logger.warn("No se pudo enviar notificación: pasantía con ID {} no existe.", idPasantias);
            return CompletableFuture.completedFuture(0);
        }

        Pasantias pasantia = pasantiaOpt.get();
        if (pasantia.getPasantiascarrerasList() == null || pasantia.getPasantiascarrerasList().isEmpty()) {
            logger.warn("Pasantía ID {} no tiene carreras asociadas para notificación.", idPasantias);
            return CompletableFuture.completedFuture(0);
        }

        List<Integer> carreraIds = pasantia.getPasantiascarrerasList().stream()
                .filter(pc -> pc.getCarrerasIdcarreras() != null)
                .map(pc -> pc.getCarrerasIdcarreras().getIdcarreras())
                .toList();

        if (carreraIds.isEmpty()) {
            return CompletableFuture.completedFuture(0);
        }

        List<PadronEstudiante> destinatarios = padronEstudianteDao.findEstudiantesActivosPorCarreras(carreraIds);
        if (destinatarios.isEmpty()) {
            logger.info("No se encontraron estudiantes activos en el padrón para las carreras de la pasantía ID {}.", idPasantias);
            return CompletableFuture.completedFuture(0);
        }

        String tituloEscapado = HtmlUtils.htmlEscape(pasantia.getTitulo() != null ? pasantia.getTitulo() : "Pasantía");
        String empresaEscapada = pasantia.getInstitucionesIdinstituciones() != null && pasantia.getInstitucionesIdinstituciones().getNombre() != null
                ? HtmlUtils.htmlEscape(pasantia.getInstitucionesIdinstituciones().getNombre())
                : "Institución participante";

        String enlaceDetalle = frontendUrl + "/pasantias/" + pasantia.getIdpasantias() + "/detalle?ref=mail";
        String subject = "Nueva convocatoria de pasantía: " + pasantia.getTitulo();

        int exitos = 0;
        int fallos = 0;

        for (PadronEstudiante estudiante : destinatarios) {
            String correo = estudiante.getCorreo();
            if (correo == null || correo.isBlank()) {
                continue;
            }

            // Evitar envíos duplicados a un mismo estudiante para esta pasantía
            if (envioCorreoPasantiaDao.existsByPasantia_IdpasantiasAndCorreoDestinatario(pasantia.getIdpasantias(), correo.trim())) {
                continue;
            }

            String bodyHtml = buildEmailHtml(estudiante.getNombres(), tituloEscapado, empresaEscapada, pasantia.getFechacierre(), enlaceDetalle);
            EmailRequest emailRequest = new EmailRequest(correo.trim(), subject, bodyHtml);

            try {
                emailService.enviarCorreo(emailRequest);

                EnvioCorreoPasantia auditoria = new EnvioCorreoPasantia(
                        pasantia,
                        estudiante,
                        correo.trim(),
                        "ENVIADO",
                        new Date()
                );
                envioCorreoPasantiaDao.save(auditoria);
                exitos++;
            } catch (Exception ex) {
                logger.error("Error al despachar correo a destinatario en pasantía ID {}: {}", idPasantias, ex.getMessage());
                EnvioCorreoPasantia auditoria = new EnvioCorreoPasantia(
                        pasantia,
                        estudiante,
                        correo.trim(),
                        "FALLIDO",
                        new Date()
                );
                auditoria.setErrorMensaje(ex.getMessage());
                envioCorreoPasantiaDao.save(auditoria);
                fallos++;
            }
        }

        logger.info("Notificación completada para pasantía ID {}: {} enviados exitosamente, {} fallidos.", idPasantias, exitos, fallos);
        return CompletableFuture.completedFuture(exitos);
    }

    private String buildEmailHtml(String nombreEstudiante, String titulo, String empresa, Date fechaCierre, String enlace) {
        String saludo = nombreEstudiante != null && !nombreEstudiante.isBlank()
                ? "Estimado/a " + HtmlUtils.htmlEscape(nombreEstudiante) + ","
                : "Estimado/a estudiante,";

        String fechaStr = fechaCierre != null ? fechaCierre.toString() : "Convocatoria abierta";

        return "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 8px;\">" +
                "<h2 style=\"color: #0891b2; margin-top: 0;\">Universidad Católica Boliviana — USEI</h2>" +
                "<p>" + saludo + "</p>" +
                "<p>Se ha publicado una nueva oportunidad de práctica preprofesional relacionada con tu carrera:</p>" +
                "<div style=\"background-color: #f8fafc; padding: 16px; border-radius: 6px; margin: 20px 0; border-left: 4px solid #0891b2;\">" +
                "<h3 style=\"margin-top: 0; color: #1e293b;\">" + titulo + "</h3>" +
                "<p style=\"margin: 6px 0; color: #475569;\"><strong>Institución / Empresa:</strong> " + empresa + "</p>" +
                "<p style=\"margin: 6px 0; color: #475569;\"><strong>Fecha límite para postular:</strong> " + fechaStr + "</p>" +
                "</div>" +
                "<p style=\"text-align: center; margin: 30px 0;\">" +
                "<a href=\"" + enlace + "\" style=\"background-color: #0891b2; color: #ffffff; padding: 12px 24px; text-decoration: none; border-radius: 6px; font-weight: bold; display: inline-block;\">" +
                "Ver Convocatoria y Postular" +
                "</a>" +
                "</p>" +
                "<hr style=\"border: none; border-top: 1px solid #e2e8f0; margin: 20px 0;\" />" +
                "<p style=\"font-size: 11px; color: #94a3b8; text-align: center;\">" +
                "Este correo fue generado automáticamente por la Unidad de Servicios Estudiantiles Integrales (USEI) UCB La Paz para estudiantes del padrón oficial." +
                "</p>" +
                "</div>";
    }
}
