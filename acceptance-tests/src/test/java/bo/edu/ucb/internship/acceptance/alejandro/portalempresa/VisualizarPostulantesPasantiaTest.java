package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.irARuta;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.loginComoEmpresa;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.optionalProperty;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;

/**
 * Caso de aceptacion para IQA-1-67: una empresa puede visualizar la lista de estudiantes
 * postulantes a una de sus pasantias.
 *
 * Dato de QA: -Dqa.empresa.pasantiaConPostulantesId=<id de una pasantia con postulantes>
 * (por defecto 3). La pasantia indicada debe tener al menos un postulante registrado.
 */
public class VisualizarPostulantesPasantiaTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final By SECCION_POSTULANTES =
        By.xpath("//*[contains(normalize-space(),'Postulantes a la pasantia')]");

    @Test
    public void muestraLaListaDePostulantesDeUnaPasantia() {
        // ID de TestLink: IQA-1-67

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        String pasantiaId = optionalProperty(
            "qa.empresa.pasantiaConPostulantesId", "QA_EMPRESA_PASANTIA_CON_POSTULANTES_ID", "3");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);

        // ===== Lógica =====
        irARuta(driver, wait, appBaseUrl(),
            "/empresa/administrador/pasantias/informacion/" + pasantiaId);

        // ===== Verificación / Assert =====
        // Con postulantes, la interfaz muestra el encabezado "Postulantes a la pasantia" y las
        // tarjetas de cada estudiante. Se verifica que la seccion de postulantes sea visible.
        boolean seVePostulantes = !wait
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(SECCION_POSTULANTES))
            .isEmpty();
        Assert.assertTrue(seVePostulantes,
            "La pantalla de detalle de la pasantia (id " + pasantiaId + ") deberia mostrar la "
                + "seccion 'Postulantes a la pasantia' con la lista de estudiantes. Verifica que la "
                + "pasantia de QA indicada tenga al menos un postulante.");
    }
}
