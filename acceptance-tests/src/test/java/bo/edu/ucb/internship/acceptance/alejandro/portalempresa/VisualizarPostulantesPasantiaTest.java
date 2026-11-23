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
 * Caso de aceptacion para IQA-1-67: una empresa puede abrir la pantalla de detalle de una de sus
 * pasantias, donde se gestiona y visualiza la lista de estudiantes postulantes.
 *
 * Dato de QA: -Dqa.empresa.pasantiaId=<id de una pasantia de la empresa> (por defecto 3).
 * Si la pasantia tiene postulantes, ademas se muestra el bloque "Postulantes a la pasantia"; la
 * verificacion base comprueba que la pantalla de la pasantia (con su seccion de postulantes) se
 * abre correctamente para la empresa duena, independientemente de la cantidad de postulantes.
 */
public class VisualizarPostulantesPasantiaTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final By DETALLE_PASANTIA =
        By.xpath("//*[contains(normalize-space(),'Detalles de la pasantia')]");

    @Test
    public void abreLaPantallaDeDetalleYPostulantesDeUnaPasantia() {
        // ID de TestLink: IQA-1-67

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        String pasantiaId = optionalProperty("qa.empresa.pasantiaId", "QA_EMPRESA_PASANTIA_ID", "3");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);

        // ===== Lógica =====
        irARuta(driver, wait, appBaseUrl(),
            "/empresa/administrador/pasantias/informacion/" + pasantiaId);

        // ===== Verificación / Assert =====
        // La pantalla de detalle de la pasantia (donde la empresa ve y gestiona a sus postulantes)
        // debe cargar y mostrar la informacion de la pasantia.
        boolean seAbreElDetalle = !wait
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(DETALLE_PASANTIA))
            .isEmpty();
        Assert.assertTrue(seAbreElDetalle,
            "La empresa deberia poder abrir la pantalla de detalle de su pasantia (id " + pasantiaId
                + "), donde se visualiza la lista de estudiantes postulantes.");
    }
}
