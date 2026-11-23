package bo.edu.ucb.internship.acceptance.antonio.autenticacionpadron;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Utilidades de interfaz compartidas para las pruebas de aceptación del Módulo 1
 * (Autenticación, Seguridad y Padrón Estudiantil - Antonio Yujra).
 */
public final class AuthPadronUiHelper {

    private static final Duration ESPERA_DEFAULT = Duration.ofSeconds(15);
    private static final By LOADING_OVERLAY = By.cssSelector(".vl-overlay");

    private AuthPadronUiHelper() {
    }

    public static WebDriverWait esperar(WebDriver driver) {
        return esperar(driver, ESPERA_DEFAULT);
    }

    public static WebDriverWait esperar(WebDriver driver, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    public static String getUsuarioEstudiante() {
        return getDato("qa.user", "QA_USER");
    }

    public static String getPasswordEstudiante() {
        return getDato("qa.password", "QA_PASSWORD");
    }

    public static String getUsuarioAdmin() {
        return getDato("qa.admin.user", "QA_ADMIN_USERNAME");
    }

    public static String getPasswordAdmin() {
        return getDato("qa.admin.password", "QA_ADMIN_PASSWORD");
    }

    private static String getDato(String prop, String env) {
        String val = System.getProperty(prop);
        if (val == null || val.isBlank()) {
            val = System.getenv(env);
        }
        if (val == null || val.isBlank()) {
            throw new SkipException("Faltan credenciales de QA: usar -D" + prop + " o la variable " + env + " para ejecutar este flujo autenticado.");
        }
        return val.trim();
    }

    public static void iniciarSesionKeycloak(WebDriver driver, WebDriverWait wait, String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).clear();
        driver.findElement(By.id("username")).sendKeys(username);

        WebElement passField = driver.findElement(By.id("password"));
        passField.clear();
        passField.sendKeys(password);

        driver.findElement(By.id("kc-login")).click();
    }

    public static void esperarOverlay(WebDriver driver, WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING_OVERLAY));
        } catch (Exception ignored) {
        }
    }

    public static void clickWhenReady(WebDriver driver, WebDriverWait wait, By locator) {
        wait.until(d -> {
            try {
                WebElement element = d.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) d;
                js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
                try {
                    element.click();
                } catch (ElementNotInteractableException e) {
                    js.executeScript("arguments[0].click();", element);
                }
                return true;
            } catch (StaleElementReferenceException | ElementClickInterceptedException retry) {
                return false;
            }
        });
    }

    public static Path crearArchivoInvalido(String nombre, String contenido) throws IOException {
        Path tempDir = Files.createTempDirectory("iqa-padron-invalido-");
        Path file = tempDir.resolve(nombre);
        Files.writeString(file, contenido, StandardCharsets.UTF_8);
        return file;
    }
}
