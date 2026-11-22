package bo.edu.ucb.internship.acceptance.antonio.autenticacionpadron;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

/**
 * ID de TestLink: IQA-1-78
 *
 * Historia de usuario: Como estudiante autenticado, si intento acceder manualmente a rutas administrativas
 * exclusivas (Route Guard con rol ADMIN), el sistema debe bloquear mi acceso y redirigirme a la pantalla principal.
 *
 * Prueba de aceptación: Verificar la restricción de acceso y redirección ante rutas administrativas no autorizadas.
 */
public class Iqa1_78RestriccionRutaAdminTest extends BaseTest {

    @Test
    public void estudianteNoPuedeAccederARutaAdministrativaYEsRedirigido() {
        // ===== PREPARACIÓN =====
        // Iniciar sesión con rol ESTUDIANTE para tener una sesión activa no administrativa.
        driver.get(appBaseUrl());
        WebDriverWait wait = AuthPadronUiHelper.esperar(driver);
        SoftAssert soft = new SoftAssert();

        String usuario = AuthPadronUiHelper.getUsuarioEstudiante();
        String password = AuthPadronUiHelper.getPasswordEstudiante();

        AuthPadronUiHelper.clickWhenReady(driver, wait, By.linkText("Iniciar Sesión"));
        AuthPadronUiHelper.iniciarSesionKeycloak(driver, wait, usuario, password);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/realms/")));
        AuthPadronUiHelper.esperarOverlay(driver, wait);

        // ===== LÓGICA =====
        // Paso 1: Intentar navegar directamente mediante la URL a la ruta protegida del Dashboard Administrativo.
        String rutaAdmin = appBaseUrl() + "/administrador/dashboard";
        driver.get(rutaAdmin);

        // Paso 2: Esperar que el Route Guard de Vue Router procese la restricción basada en roles.
        AuthPadronUiHelper.esperarOverlay(driver, wait);

        // Esperar a que la URL se estabilice fuera del panel de administración.
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/administrador/dashboard")));

        // ===== VERIFICACIÓN / ASSERT =====
        // Verificar que la URL actual no permanezca en la ruta administrativa.
        soft.assertFalse(driver.getCurrentUrl().contains("/administrador/dashboard"),
                "El Route Guard debía bloquear el acceso a /administrador/dashboard.");

        // Verificar que ningún componente visual administrativo (sidebar de admin o título) esté montado en pantalla.
        List<WebElement> navbarAdmin = driver.findElements(By.cssSelector("nav.navbar-admin, .admin-sidebar"));
        soft.assertTrue(navbarAdmin.isEmpty(),
                "No debe renderizarse ningún componente de navegación exclusivo de administradores.");

        // Verificar que la navegación haya retornado a la raíz o vista autorizada para el estudiante.
        soft.assertTrue(driver.getCurrentUrl().equals(appBaseUrl() + "/") || driver.getCurrentUrl().equals(appBaseUrl()),
                "El Route Guard debía redirigir al usuario a la página de inicio pública o autorizada.");

        soft.assertAll();
    }
}
