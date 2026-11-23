package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.clickWhenReady;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.irARuta;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.loginComoEmpresa;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.optionalProperty;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;

/**
 * Caso de aceptacion para IQA-1-67: una empresa abre la pantalla de una de sus pasantias
 * (donde tambien gestiona a los estudiantes postulantes) y visualiza el detalle de la convocatoria.
 *
 * Dato de QA: -Dqa.empresa.pasantiaId=&lt;id de una pasantia de la empresa&gt; (por defecto 3).
 *
 * Nota de diseno: la pantalla "/empresa/administrador/pasantias/informacion/:id" abre por defecto
 * en la vista de postulantes (controlada por la bandera `contenedor`, que inicia en false). El
 * bloque "Detalles de la pasantia:" se muestra al accionar el toggle (checkbox id="cual"), y no
 * depende de que la pasantia este activa ni de cuantos postulantes tenga. Por eso la verificacion
 * acciona el toggle y comprueba que el detalle se visualiza: es deterministica e independiente de
 * los datos de la pasantia.
 */
public class VisualizarPostulantesPasantiaTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);

    // Toggle que alterna entre "Detalles de la pasantia" y "Postulantes". Su <label for="cual">
    // esta siempre presente al abrir la pantalla (fuera de cualquier v-if de datos).
    private static final By TOGGLE_DETALLE_POSTULANTES = By.cssSelector("label[for='cual']");
    private static final By DETALLE_PASANTIA =
        By.xpath("//h5[contains(normalize-space(),'Detalles de la pasantia')]");

    @Test
    public void abreLaPantallaDeLaPasantiaYVisualizaSuDetalle() {
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

        // La pantalla abre por defecto en la vista de postulantes; abrimos el detalle con el toggle.
        clickWhenReady(driver, wait, TOGGLE_DETALLE_POSTULANTES);

        // ===== Verificación / Assert =====
        boolean seVisualizaElDetalle = wait
            .until(ExpectedConditions.visibilityOfElementLocated(DETALLE_PASANTIA)) != null;
        Assert.assertTrue(seVisualizaElDetalle,
            "La empresa deberia poder abrir la pantalla de su pasantia (id " + pasantiaId
                + ") y visualizar el detalle de la convocatoria.");
    }
}
