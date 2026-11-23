package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.SWAL_POPUP;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.clickWhenReady;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.irARuta;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.loginComoEmpresa;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.textoDelSweetAlert;

/**
 * Caso de aceptacion para IQA-1-64: el formulario de creacion de pasantia no debe permitir
 * publicar cuando faltan campos obligatorios.
 */
public class ValidacionCamposObligatoriosCrearPasantiaTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final String RUTA_CREAR = "/empresa/administrador/pasantias/crear";
    private static final By BOTON_GUARDAR = By.xpath("//a[normalize-space()='Guardar Pasantía']");

    @Test
    public void noPublicaUnaPasantiaConCamposObligatoriosVacios() {
        // ID de TestLink: IQA-1-64

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);
        irARuta(driver, wait, appBaseUrl(), RUTA_CREAR);

        // ===== Lógica =====
        // Se intenta publicar sin llenar ningun campo obligatorio.
        clickWhenReady(driver, wait, BOTON_GUARDAR);

        // ===== Verificación / Assert =====
        wait.until(ExpectedConditions.visibilityOfElementLocated(SWAL_POPUP));
        String alerta = textoDelSweetAlert(driver, wait);
        Assert.assertTrue(alerta.contains("Debes llenar todos los campos"),
            "Al publicar una pasantia sin completar los campos obligatorios, la interfaz deberia "
                + "mostrar el mensaje de validacion 'Debes llenar todos los campos'. Texto mostrado: " + alerta);
    }
}
