package bo.edu.ucb.internship.acceptance.jaime.portalpublico;

import static org.testng.Assert.assertTrue;

import bo.edu.ucb.internship.acceptance.BaseTest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * ID de TestLink: IQA-1-27
 *
 * Historia de usuario: como visitante sin sesión quiero que, al abrir una pasantía, la aplicación me lleve
 * a iniciar sesión y luego me devuelva a esa misma pasantía.
 *
 * Prueba de aceptación: verificar que un usuario sin sesión es dirigido al inicio de sesión al abrir una pasantía.
 *
 * Datos de QA: pasantía 14 ("Pasantía de apoyo 14") del entorno local; cuenta de estudiante en qa.user / qa.password.
 *
 * Resultado esperado: Keycloak aparece guardando la ruta de retorno; tras iniciar sesión se vuelve al detalle
 * y el botón "Postula ahora" está disponible.
 */
public class Iqa1_27LoginConRetornoTest extends BaseTest {

    private static final String RUTA_PASANTIA = "/pasantias/14/detalle";

    @Test
    public void sinSesionRedirigeAKeycloakYRegresaALaPasantia() {
        // ===== PREPARACIÓN =====
        // Chrome se abre limpio (BaseTest), por lo tanto no hay sesión iniciada.
        // Se leen las credenciales de QA; si faltan, la prueba se omite.
        String usuario = AccesoPortal.usuario();
        String password = AccesoPortal.password();
        WebDriverWait espera = AccesoPortal.esperar(driver);

        // ===== LÓGICA =====
        // Paso 1: abrir directamente la ruta de una pasantía sin haber iniciado sesión.
        driver.get(appBaseUrl() + RUTA_PASANTIA);
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        String urlDeInicioDeSesion = URLDecoder.decode(driver.getCurrentUrl(), StandardCharsets.UTF_8);

        // Paso 2: iniciar sesión con la cuenta de estudiante desde la pantalla de Keycloak.
        driver.findElement(By.id("username")).sendKeys(usuario);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("kc-login")).click();

        // Paso 3: esperar a volver al detalle de la pasantía y localizar el botón de postulación.
        espera.until(ExpectedConditions.urlContains(RUTA_PASANTIA));
        WebElement botonPostular = espera.until(
                ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Postula ahora")));

        // ===== VERIFICACIÓN / ASSERT =====
        assertTrue(urlDeInicioDeSesion.contains("/realms/"),
                "Sin sesión debía mostrarse el inicio de sesión de Keycloak, pero la URL fue: " + urlDeInicioDeSesion);
        assertTrue(urlDeInicioDeSesion.contains("redirect_uri=" + appBaseUrl() + RUTA_PASANTIA),
                "Keycloak debía guardar la ruta de retorno " + RUTA_PASANTIA + " pero la URL fue: " + urlDeInicioDeSesion);
        assertTrue(driver.getCurrentUrl().contains(RUTA_PASANTIA),
                "Tras iniciar sesión debía volverse a " + RUTA_PASANTIA + " pero se quedó en: " + driver.getCurrentUrl());
        assertTrue(botonPostular.isDisplayed(), "El botón \"Postula ahora\" debía estar disponible");
    }
}
