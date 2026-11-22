package bo.edu.ucb.internship.acceptance.jaime.portalpublico;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * ID de TestLink: IQA-1-28
 *
 * Historia de usuario: como visitante quiero ver cómo contactar a la USEI (formulario, teléfono, correo,
 * dirección y mapa) sin necesidad de iniciar sesión.
 *
 * Prueba de aceptación: verificar que la página de contacto muestra la información institucional.
 *
 * Datos de QA: datos oficiales de la USEI mostrados por la aplicación.
 *
 * Resultado esperado: se abre "¡Contáctanos!" con el formulario, el teléfono con su horario, el correo, la
 * dirección y el mapa de Google.
 */
public class Iqa1_28ContactoTest extends BaseTest {

    @Test
    public void paginaDeContactoMuestraFormularioDatosYMapa() {
        // ===== PREPARACIÓN =====
        // No se inicia sesión: la página de contacto es pública. Se abre desde el menú superior.
        driver.get(appBaseUrl());
        WebDriverWait espera = AccesoPortal.esperar(driver);
        SoftAssert verificacion = new SoftAssert();

        // ===== LÓGICA =====
        // Paso 1: hacer clic en "Contactos" del menú superior y esperar el formulario.
        espera.until(ExpectedConditions.elementToBeClickable(By.linkText("Contactos"))).click();
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        // Pasos 2 a 4: leer el formulario, los datos de contacto y el mapa (solo observar, no se envía nada).
        String pantalla = driver.findElement(By.tagName("body")).getText();

        // ===== VERIFICACIÓN / ASSERT =====
        verificacion.assertTrue(driver.getCurrentUrl().contains("/contactanos"), "Debía abrirse /contactanos");
        // Formulario
        verificacion.assertTrue(driver.findElement(By.id("name")).isDisplayed(), "Debía existir el campo de nombre");
        verificacion.assertTrue(driver.findElement(By.id("email")).isDisplayed(), "Debía existir el campo de correo");
        verificacion.assertTrue(driver.findElement(By.id("comments")).isDisplayed(), "Debía existir el campo de comentario");
        verificacion.assertTrue(pantalla.contains("Enviar correo"), "Debía existir el botón Enviar correo");
        // Datos de contacto de la USEI
        verificacion.assertTrue(pantalla.contains("+591 76521445"), "Debía mostrar el teléfono");
        verificacion.assertTrue(pantalla.contains("Lunes a jueves de 08:30 a las 16:00"), "Debía mostrar el horario");
        verificacion.assertTrue(pantalla.contains("usei.lpz@ucb.edu.bo"), "Debía mostrar el correo");
        verificacion.assertTrue(pantalla.contains("Av. 14 de Septiembre"), "Debía mostrar la dirección");
        // Mapa de Google (incrustado) y enlace a Google Maps
        verificacion.assertFalse(driver.findElements(By.cssSelector("iframe[src*='google.com/maps']")).isEmpty(),
                "Debía mostrarse el mapa de Google incrustado");
        verificacion.assertFalse(driver.findElements(By.linkText("Ver en Google Maps")).isEmpty(),
                "Debía existir el enlace 'Ver en Google Maps'");

        verificacion.assertAll();
    }
}
