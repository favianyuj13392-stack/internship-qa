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
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;

/**
 * Caso de aceptacion para IQA-1-XX (Portal Empresa): al ingresar, la empresa visualiza la pantalla
 * con el listado de sus pasantias publicadas, con el encabezado "Pasantias de ..." y el boton para
 * crear una nueva convocatoria.
 *
 * Verificacion deterministica: el encabezado (gated solo por sesion autenticada) y el boton
 * "Crear pasantia" se renderizan siempre para una empresa, sin depender de cuantas pasantias tenga.
 */
public class ListarPasantiasEmpresaTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);

    private static final By ENCABEZADO_LISTADO =
        By.xpath("//h3[contains(normalize-space(),'Pasant')]");
    private static final By BOTON_CREAR_PASANTIA =
        By.xpath("//button[contains(normalize-space(),'Crear')]");

    @Test
    public void visualizaElListadoDeSusPasantias() {
        // ID de TestLink: IQA-1-XX (por confirmar con el plan de ejecucion)

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);

        // ===== Lógica =====
        irARuta(driver, wait, appBaseUrl(), "/empresa/administrador/pasantias");

        // ===== Verificación / Assert =====
        boolean hayEncabezado = !wait
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(ENCABEZADO_LISTADO))
            .isEmpty();
        boolean hayBotonCrear = !driver.findElements(BOTON_CREAR_PASANTIA).isEmpty();

        Assert.assertTrue(hayEncabezado && hayBotonCrear,
            "La empresa deberia ver la pantalla de listado de sus pasantias "
                + "(encabezado 'Pasantias de ...' y boton 'Crear pasantia').");
    }
}
