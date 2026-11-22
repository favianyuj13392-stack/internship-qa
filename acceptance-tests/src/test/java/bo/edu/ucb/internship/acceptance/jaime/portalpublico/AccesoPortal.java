package bo.edu.ucb.internship.acceptance.jaime.portalpublico;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

/**
 * Utilidades compartidas por las pruebas del Módulo 2 (Portal Público y Catálogo).
 * No es una prueba: solo agrupa pasos repetidos (iniciar sesión por la interfaz, esperas, escribir en un campo).
 *
 * Las credenciales NO están en el código. Se leen de la propiedad -Dqa.user / -Dqa.password
 * o de las variables de entorno QA_USER / QA_PASSWORD (cuenta de estudiante del entorno local de QA).
 */
final class AccesoPortal {

    private static final Duration ESPERA_MAXIMA = Duration.ofSeconds(15);

    private AccesoPortal() {
    }

    static String usuario() {
        return dato("qa.user", "QA_USER");
    }

    static String password() {
        return dato("qa.password", "QA_PASSWORD");
    }

    private static String dato(String propiedad, String variableDeEntorno) {
        String valor = System.getProperty(propiedad);
        if (valor == null || valor.isBlank()) {
            valor = System.getenv(variableDeEntorno);
        }
        if (valor == null || valor.isBlank()) {
            // Sin credenciales la prueba se omite (no falla) y el reporte explica por qué.
            throw new SkipException("Faltan credenciales de QA: usar -D" + propiedad + " o la variable " + variableDeEntorno);
        }
        return valor;
    }

    /** Espera explícita de Selenium (sin pausas fijas), tolerante a que Vue vuelva a pintar la pantalla. */
    static WebDriverWait esperar(WebDriver driver) {
        WebDriverWait espera = new WebDriverWait(driver, ESPERA_MAXIMA);
        espera.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
        return espera;
    }

    /**
     * Abre una ruta protegida, inicia sesión desde la pantalla de Keycloak (como lo haría una persona)
     * y espera a que la aplicación regrese a esa misma ruta.
     */
    static void iniciarSesionYAbrir(WebDriver driver, String urlBase, String ruta) {
        driver.get(urlBase + ruta);
        iniciarSesionEnKeycloak(driver);
        esperar(driver).until(ExpectedConditions.urlContains(ruta));
    }

    /** Llena usuario y contraseña en la pantalla de Keycloak y pulsa "Sign In". */
    static void iniciarSesionEnKeycloak(WebDriver driver) {
        WebDriverWait espera = esperar(driver);
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(usuario());
        driver.findElement(By.id("password")).sendKeys(password());
        driver.findElement(By.id("kc-login")).click();
    }

    /** Reemplaza el contenido de un campo de texto escribiendo como una persona (selecciona todo y escribe). */
    static void escribir(WebElement campo, String texto) {
        Keys modificador = System.getProperty("os.name", "").toLowerCase().contains("mac") ? Keys.COMMAND : Keys.CONTROL;
        campo.click();
        campo.sendKeys(Keys.chord(modificador, "a"));
        campo.sendKeys(texto);
    }
}
