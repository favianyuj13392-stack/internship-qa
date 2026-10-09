package ucb.edu.bo.internship.internship_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ucb.edu.bo.internship.internship_backend.bl.ActividadTrackingService;
import ucb.edu.bo.internship.internship_backend.dao.PadronEstudianteDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class RosterSecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RosterSecurityFilter.class);
    private static final Pattern UCB_EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\\.)?ucb\\.edu\\.bo$");
    private final PadronEstudianteDao padronEstudianteDao;
    private final UsuariosDao usuariosDao;
    private final ActividadTrackingService actividadTrackingService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RosterSecurityFilter(PadronEstudianteDao padronEstudianteDao,
                                UsuariosDao usuariosDao,
                                ActividadTrackingService actividadTrackingService) {
        this.padronEstudianteDao = padronEstudianteDao;
        this.usuariosDao = usuariosDao;
        this.actividadTrackingService = actividadTrackingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Solo aplica para rutas de estudiantes y consulta de pasantías
        if (!path.startsWith("/api/v1/estudiante") && !path.startsWith("/api/v1/pasantia")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Solo se aplica restricción de padrón si el usuario tiene rol ESTUDIANTE
        boolean isStudent = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ESTUDIANTE"));

        if (!isStudent) {
            filterChain.doFilter(request, response);
            return;
        }

        Jwt jwt = null;
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            jwt = jwtAuth.getToken();
        } else if (auth.getPrincipal() instanceof Jwt principalJwt) {
            jwt = principalJwt;
        }

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwt.getClaimAsString("email");
        String kcUuid = jwt.getSubject();

        if (email == null || !UCB_EMAIL_PATTERN.matcher(email.toLowerCase().trim()).matches()) {
            logger.warn("Acceso bloqueado: El correo del estudiante {} no pertenece al dominio @ucb.edu.bo", email);
            sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, "NON_INSTITUTIONAL_EMAIL",
                    "Solo se admiten correos institucionales de la Universidad Católica Boliviana (@ucb.edu.bo)");
            return;
        }

        Optional<PadronEstudiante> padronOpt = padronEstudianteDao.findByCorreoIgnoreCase(email.toLowerCase().trim());
        if (padronOpt.isEmpty()) {
            logger.warn("Acceso bloqueado: El correo del estudiante {} no figura en el padrón de USEI", email);
            sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, "ROSTER_ACCESS_DENIED",
                    "Tu correo no se encuentra registrado en el padrón oficial de prácticas preprofesionales de USEI.");
            return;
        }

        PadronEstudiante estudiante = padronOpt.get();
        if (!"ACTIVO".equalsIgnoreCase(estudiante.getEstado())) {
            logger.warn("Acceso bloqueado: El estudiante {} tiene estado {}", email, estudiante.getEstado());
            sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, "ROSTER_INACTIVE",
                    "Tu cuenta figura como inactiva en el padrón de prácticas. Contactate con la unidad USEI.");
            return;
        }

        // Registro de acceso y vinculación automática
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        } else if (ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        actividadTrackingService.registrarAcceso(email, kcUuid, "PORTAL", ip);

        filterChain.doFilter(request, response);
    }

    private void sendJsonError(HttpServletResponse response, int status, String code, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();
        responseDto.setCode(String.valueOf(status));
        responseDto.setErrorMessage(message);
        responseDto.setResponse(Map.of("errorCode", code, "message", message));

        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }
}
