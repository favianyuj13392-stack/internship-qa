package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;

import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.clickWhenReady;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.esperarOverlayDeCarga;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.irARuta;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.loginComoEmpresa;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;

/**
 * Caso de aceptacion para IQA-1-63: una empresa aprobada puede publicar una nueva convocatoria de
 * pasantia completando el formulario con datos validos.
 *
 * Es el camino feliz complementario al IQA-1-64 (que verifica la validacion con el formulario
 * vacio). Aqui se llenan TODOS los campos obligatorios por la interfaz (titulo, descripcion, fecha
 * de cierre futura, un area, una carrera, y un requisito/funcion/beneficio via los dialogos de
 * SweetAlert) y se comprueba que la pasantia se guarda: al guardarse con exito la aplicacion
 * redirige al listado de pasantias de la empresa.
 *
 * Nota: la prueba CREA una pasantia real (con titulo unico por timestamp) en el entorno bajo
 * prueba. Toda la interaccion es por la UI (Chrome -> Vue -> Spring Boot).
 */
public class PublicarConvocatoriaEmpresaTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(25);

    private static final By TITULO = By.xpath("//input[@placeholder='Título Pasantía']");
    private static final By DESCRIPCION = By.xpath("//textarea[@placeholder='Escribe aquí...']");
    private static final By FECHA_CIERRE = By.cssSelector("input[type='date']");
    private static final By AREA_SELECT =
        By.xpath("//select[option[normalize-space()='Seleccionar Área']]");
    private static final By CARRERA_SELECT =
        By.xpath("//select[option[normalize-space()='Seleccionar Carrera']]");
    private static final By BTN_REQUISITOS =
        By.xpath("//h5[contains(normalize-space(),'Requis')]/following-sibling::button");
    private static final By BTN_FUNCIONES =
        By.xpath("//h5[normalize-space()='Funciones:']/following-sibling::button");
    private static final By BTN_BENEFICIOS =
        By.xpath("//h5[normalize-space()='Beneficios:']/following-sibling::button");
    private static final By GUARDAR =
        By.xpath("//a[contains(normalize-space(),'Guardar Pasantía')]");

    private static final By SWAL_INPUT = By.cssSelector(".swal2-input");
    private static final By SWAL_CONFIRM = By.cssSelector("button.swal2-confirm");
    private static final By SWAL_POPUP = By.cssSelector(".swal2-popup");

    @Test
    public void publicaUnaNuevaConvocatoriaConDatosValidos() {
        // ID de TestLink: IQA-1-63

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);
        irARuta(driver, wait, appBaseUrl(), "/empresa/administrador/pasantias/crear");

        // ===== Lógica: llenar el formulario con datos válidos =====
        String tituloUnico = "QA Pasantia Aceptacion " + System.currentTimeMillis();
        wait.until(ExpectedConditions.visibilityOfElementLocated(TITULO)).sendKeys(tituloUnico);
        driver.findElement(DESCRIPCION)
            .sendKeys("Pasantia creada por la prueba de aceptacion automatizada (IQA-1-63).");

        // Fecha de cierre futura (hoy + 30 días). El input date se setea por JS y se despachan
        // los eventos para que Vue (v-model) tome el valor de forma estable en cualquier locale.
        String fechaFutura = LocalDate.now().plusDays(30).toString(); // yyyy-MM-dd
        WebElement inputFecha = driver.findElement(FECHA_CIERRE);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].value = arguments[1];"
                + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            inputFecha, fechaFutura);

        // Área: seleccionar la primera opción real (índice 0 es el placeholder deshabilitado).
        wait.until(d -> new Select(d.findElement(AREA_SELECT)).getOptions().size() > 2);
        new Select(driver.findElement(AREA_SELECT)).selectByIndex(1);

        // Carrera: seleccionar la primera carrera real.
        wait.until(d -> new Select(d.findElement(CARRERA_SELECT)).getOptions().size() > 1);
        new Select(driver.findElement(CARRERA_SELECT)).selectByIndex(1);

        // Requisito, función y beneficio: se agregan por diálogos de SweetAlert.
        agregarItemConSwal(wait, BTN_REQUISITOS, "Manejo de Java y Spring Boot");
        agregarItemConSwal(wait, BTN_FUNCIONES, "Desarrollo de APIs REST");
        agregarItemConSwal(wait, BTN_BENEFICIOS, "Certificado de pasantia");

        // ===== Guardar =====
        clickWhenReady(driver, wait, GUARDAR);

        // ===== Verificación / Assert =====
        // Al guardarse con éxito, la app redirige al listado de pasantías de la empresa
        // (sale de la ruta '/crear'). Si el guardado fallara, se queda en el formulario.
        boolean seGuardoYRedirigio = wait.until(ExpectedConditions.and(
            ExpectedConditions.urlContains("/empresa/administrador/pasantias"),
            ExpectedConditions.not(ExpectedConditions.urlContains("/crear"))));
        Assert.assertTrue(seGuardoYRedirigio,
            "Al publicar una convocatoria con datos válidos, la empresa debería guardarla y ser "
                + "redirigida al listado de sus pasantías.");
    }

    /** Agrega un ítem mediante el diálogo de SweetAlert (Requisito / Función / Beneficio). */
    private void agregarItemConSwal(WebDriverWait wait, By botonAgregar, String texto) {
        clickWhenReady(driver, wait, botonAgregar);
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(SWAL_INPUT));
        input.clear();
        input.sendKeys(texto);
        clickWhenReady(driver, wait, SWAL_CONFIRM);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(SWAL_POPUP));
    }
}
