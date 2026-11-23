package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.SWAL_POPUP;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.clickWhenReady;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.irARuta;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.loginComoEmpresa;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredProperty;

/**
 * Caso de aceptacion para IQA-1-66: una empresa puede cerrar/terminar una convocatoria que no
 * tiene postulantes, y la interfaz confirma el cierre.
 *
 * Dato de QA (obligatorio, porque la accion es destructiva): el id de una pasantia de la empresa
 * sin postulantes, preparada para este caso.
 *   -Dqa.empresa.pasantiaSinPostulantesId=<id>
 */
public class TerminarConvocatoriaSinPostulantesTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final By BOTON_TERMINAR = By.xpath("//*[normalize-space()='Terminar Pasantia']");
    private static final By CONFIRMAR_TERMINAR = By.xpath("//button[normalize-space()='Sí, terminar']");

    @Test
    public void terminaUnaConvocatoriaSinPostulantes() {
        // ID de TestLink: IQA-1-66

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        String pasantiaId = requiredProperty(
            "qa.empresa.pasantiaSinPostulantesId", "QA_EMPRESA_PASANTIA_SIN_POSTULANTES_ID",
            "Falta el id de una pasantia de QA sin postulantes para terminar (accion destructiva)");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);
        irARuta(driver, wait, appBaseUrl(),
            "/empresa/administrador/pasantias/informacion/" + pasantiaId);

        // ===== Lógica =====
        clickWhenReady(driver, wait, BOTON_TERMINAR);
        // SweetAlert de confirmacion: "¿Está seguro de terminar la pasantía?" -> "Sí, terminar".
        clickWhenReady(driver, wait, CONFIRMAR_TERMINAR);

        // ===== Verificación / Assert =====
        // Al terminar, la app muestra "Pasantía terminada" y redirige al listado de pasantias.
        boolean terminada = wait.until(d -> {
            if (d.getCurrentUrl().endsWith("/empresa/administrador/pasantias")) {
                return true;
            }
            return d.findElements(SWAL_POPUP).stream()
                .anyMatch(p -> p.getText().contains("Pasantía terminada"));
        });
        Assert.assertTrue(terminada,
            "Al terminar una convocatoria sin postulantes, la interfaz deberia confirmar "
                + "('Pasantía terminada') y redirigir al listado de pasantias de la empresa.");
    }
}
