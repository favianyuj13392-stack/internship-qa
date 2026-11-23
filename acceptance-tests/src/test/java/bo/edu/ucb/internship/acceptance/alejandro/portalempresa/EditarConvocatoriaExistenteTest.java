package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.SWAL_POPUP;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.clickWhenReady;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.irARuta;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.loginComoEmpresa;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.optionalProperty;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;

/**
 * Caso de aceptacion para IQA-1-65: una empresa puede editar una convocatoria existente y los
 * cambios se guardan mostrando la confirmacion en la interfaz.
 *
 * Dato de QA: -Dqa.empresa.pasantiaId=<id de una pasantia de la empresa> (por defecto 3).
 */
public class EditarConvocatoriaExistenteTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final By TITULO_INPUT = By.xpath("//input[@placeholder='Título Pasantía']");
    private static final By BOTON_ACTUALIZAR = By.xpath("//a[normalize-space()='Actualizar Pasantía']");

    @Test
    public void editaElTituloDeUnaConvocatoriaYSeGuarda() {
        // ID de TestLink: IQA-1-65

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        String pasantiaId = optionalProperty("qa.empresa.pasantiaId", "QA_EMPRESA_PASANTIA_ID", "3");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);
        irARuta(driver, wait, appBaseUrl(), "/empresa/administrador/pasantias/" + pasantiaId + "/editar");

        String nuevoTitulo = "QA edicion IQA-1-65 " + System.currentTimeMillis();

        // ===== Lógica =====
        WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(TITULO_INPUT));
        titulo.clear();
        titulo.sendKeys(nuevoTitulo);
        clickWhenReady(driver, wait, BOTON_ACTUALIZAR);

        // ===== Verificación / Assert =====
        // La app confirma con un SweetAlert ("Se actualizó la pasantia") y luego redirige al
        // listado de pasantias de la empresa. Se acepta cualquiera de las dos evidencias visibles.
        boolean confirmado = wait.until(d -> {
            if (d.getCurrentUrl().endsWith("/empresa/administrador/pasantias")) {
                return true;
            }
            return d.findElements(SWAL_POPUP).stream()
                .anyMatch(p -> p.getText().contains("Se actualizó la pasantia"));
        });
        Assert.assertTrue(confirmado,
            "Al actualizar la convocatoria, la interfaz deberia confirmar el guardado "
                + "('Se actualizó la pasantia') o redirigir al listado de pasantias de la empresa.");
    }
}
