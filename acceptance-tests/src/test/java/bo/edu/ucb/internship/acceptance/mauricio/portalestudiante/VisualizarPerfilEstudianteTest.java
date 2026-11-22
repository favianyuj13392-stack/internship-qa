package bo.edu.ucb.internship.acceptance.mauricio.portalestudiante;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * IQA-1-1 (TestLink, suite "Portal Estudiante"): verifica que el estudiante autenticado
 * pueda visualizar correctamente los datos de su perfil en /perfil/estudiante.
 */
public class VisualizarPerfilEstudianteTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);

    @Test
    public void visualizaDatosDelPerfilEstudiante() {
        // ID de TestLink: IQA-1-1

        // ===== Preparación =====
        // Credenciales de estudiante QA leídas fuera del repo (propiedad Maven o variable de entorno).
        String email = requiredCredential("qa.student.email", "QA_STUDENT_EMAIL");
        String password = requiredCredential("qa.student.password", "QA_STUDENT_PASSWORD");

        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        // Abrir la aplicación e iniciar sesión por la interfaz (click real en "Iniciar Sesión",
        // redirección a Keycloak, llenado del formulario de login).
        driver.get(appBaseUrl());
        clickWhenReady(wait, By.linkText("Iniciar Sesión"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("kc-login")).click();

        // Esperar a volver al dominio de la app (Keycloak redirige de vuelta tras el login)
        // antes de buscar cualquier elemento propio de la aplicación.
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/realms/")));

        // Tras volver a la app autenticado, esperar a que desaparezca el overlay de carga
        // (vue-loading-overlay, usado en toda la app) antes de abrir el menú de usuario.
        By loadingOverlay = By.cssSelector(".vl-overlay");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
        By avatarDropdownToggle = By.cssSelector("button[data-dropdown-toggle='dropdown']");
        clickWhenReady(wait, avatarDropdownToggle);
        clickWhenReady(wait, By.linkText("Perfil"));

        // ===== Lógica =====
        // Esperar a que cargue la ruta /perfil/estudiante y los datos del perfil.
        wait.until(ExpectedConditions.urlContains("/perfil/estudiante"));
        WebElement nombreEl = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contenedor-nombre h5")));
        WebElement correoEl = driver.findElement(By.id("valueCorreoEstudiante"));
        WebElement anioIngresoEl = driver.findElement(By.id("valueAnioIngresoEstudiante"));
        WebElement telefonoEl = driver.findElement(By.id("valueTelefonoEstudiante"));

        // ===== Verificación / Assert =====
        // Se despliegan nombres, correo, año de ingreso, teléfono y la sección de currículums cargados.
        Assert.assertFalse(nombreEl.getText().trim().isEmpty(),
            "El nombre del estudiante debería mostrarse en la cabecera del perfil");
        Assert.assertTrue(correoEl.getText().contains("@"),
            "El correo mostrado debería tener formato de email");
        Assert.assertFalse(anioIngresoEl.getText().trim().isEmpty(),
            "El año de ingreso a la universidad debería mostrarse");
        Assert.assertFalse(telefonoEl.getText().trim().isEmpty(),
            "El teléfono debería mostrarse");

        boolean tieneAlMenosUnCurriculum = !driver.findElements(By.cssSelector("a[download]")).isEmpty();
        Assert.assertTrue(tieneAlMenosUnCurriculum,
            "Debería listarse al menos un currículum cargado con su botón de descarga");
    }

    /**
     * Guarda una captura de pantalla cuando el caso falla (evidencia pedida por la guía ATDD).
     * Se ejecuta además de {@code BaseTest.closeBrowser()} (TestNG corre los {@code @AfterMethod}
     * de toda la jerarquía); por eso toma la captura antes de que el driver se cierre.
     */
    @AfterMethod(alwaysRun = true)
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE || driver == null) {
            return;
        }
        try {
            Path dir = Path.of("target", "surefire-reports", "screenshots");
            Files.createDirectories(dir);
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(dir.resolve(result.getMethod().getMethodName() + ".png"), png);
        } catch (IOException | ClassCastException ignored) {
            // Evidencia best-effort: si falla la captura, no debe ocultar el motivo real del fallo.
        }
    }

    /**
     * Hace clic en el elemento ubicado por {@code locator}, reubicándolo en cada intento.
     * Vue puede re-renderizar el DOM justo cuando el elemento queda "clickable", dejando
     * la referencia obtenida por el wait obsoleta (StaleElementReferenceException) o
     * momentáneamente tapada (ElementClickInterceptedException); por eso se reintenta
     * contra el locator, no contra un WebElement ya capturado.
     */
    private void clickWhenReady(WebDriverWait wait, By locator) {
        wait.until(driver -> {
            try {
                driver.findElement(locator).click();
                return true;
            } catch (StaleElementReferenceException | ElementClickInterceptedException retry) {
                return false;
            }
        });
    }

    private String requiredCredential(String systemProperty, String envVar) {
        String value = System.getProperty(systemProperty);
        if (value == null || value.isBlank()) {
            value = System.getenv(envVar);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "Falta la credencial de estudiante QA. Pasa -D" + systemProperty + "=<valor> al ejecutar Maven, "
                    + "o define la variable de entorno " + envVar + " antes de correr la prueba.");
        }
        return value;
    }
}
