package bo.edu.ucb.internship.acceptance.mauricio.portalestudiante;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
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

import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.clickGuardarEnFormulario;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.esperarOverlayDeCarga;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.loginComoEstudiante;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.requiredCredential;

public class EditarDatosPersonalesEstudianteTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final By TELEFONO_INPUT = By.cssSelector("input[placeholder='Número Celular']");
    private static final By TELEFONO_MOSTRADO = By.id("valueTelefonoEstudiante");

    // Valor de ejemplo embebido en EditarPerfilEstudiante.vue antes de que termine de cargar
    // el estudiante real (ver estudianteDto.persona.telefono en el data() del componente).
    private static final String TELEFONO_PLACEHOLDER_INICIAL = "60176430";

    @Test
    public void editaYRestauraElTelefonoDelEstudiante() {
        // ID de TestLink: IQA-1-2

        // ===== Preparación =====
        String email = requiredCredential("qa.student.email", "QA_STUDENT_EMAIL");
        String password = requiredCredential("qa.student.password", "QA_STUDENT_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEstudiante(driver, wait, appBaseUrl(), email, password);
        // Nota: se navega directo por URL a /perfil/estudiante/editar (en vez de hacer click en
        // el enlace "editarPerfil") porque EditarPerfilEstudiante.vue tiene un defecto pre-existente:
        // registrarEstudiante() convierte habilidades/experiencia/redesSociales a JSON string sobre
        // el mismo estado reactivo que usa el template, lo que deja al componente en un estado
        // inconsistente justo después de guardar. Navegar por URL fuerza una carga limpia de la
        // página y evita pelear contra ese estado corrupto, sin dejar de probar por UI real la
        // edición y persistencia de los datos personales (el objetivo de IQA-1-2).
        driver.get(appBaseUrl() + "/perfil/estudiante/editar");
        wait.until(ExpectedConditions.urlContains("editar"));

        // Espera a que el formulario termine de cargar los datos reales del estudiante
        // (reemplazando el valor de ejemplo hardcodeado) antes de leer el teléfono original.
        wait.until(d -> {
            String valor = d.findElement(TELEFONO_INPUT).getAttribute("value");
            return valor != null && !valor.isBlank() && !valor.equals(TELEFONO_PLACEHOLDER_INICIAL);
        });
        String telefonoOriginal = driver.findElement(TELEFONO_INPUT).getAttribute("value");
        String telefonoNuevo = "7" + (System.currentTimeMillis() % 1_000_000L);

        try {
            // ===== Lógica =====
            WebElement telefonoInput = driver.findElement(TELEFONO_INPUT);
            telefonoInput.clear();
            telefonoInput.sendKeys(telefonoNuevo);
            clickGuardarEnFormulario(driver, wait);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("editar")));
            esperarOverlayDeCarga(driver, wait);

            // ===== Verificación / Assert =====
            WebElement telefonoMostrado = wait.until(ExpectedConditions.visibilityOfElementLocated(TELEFONO_MOSTRADO));
            Assert.assertEquals(telefonoMostrado.getText().trim(), telefonoNuevo,
                "El teléfono mostrado en el perfil debería reflejar el valor recién editado");
        } finally {
            // Restaura el valor original por UI para no ensuciar futuras corridas, incluso si
            // la verificación de arriba falla.
            driver.get(appBaseUrl() + "/perfil/estudiante/editar");
            wait.until(ExpectedConditions.urlContains("editar"));
            WebElement telefonoInputRestaurar = wait.until(ExpectedConditions.visibilityOfElementLocated(TELEFONO_INPUT));
            telefonoInputRestaurar.clear();
            telefonoInputRestaurar.sendKeys(telefonoOriginal);
            clickGuardarEnFormulario(driver, wait);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("editar")));
        }
    }

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
            Files.writeString(dir.resolve(result.getMethod().getMethodName() + ".html"), driver.getPageSource());
            Files.writeString(dir.resolve(result.getMethod().getMethodName() + "-url.txt"), driver.getCurrentUrl());
        } catch (IOException | ClassCastException ignored) {
        }
    }
}
