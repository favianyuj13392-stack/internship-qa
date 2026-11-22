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
 * ID de TestLink: IQA-1-82
 *
 * Historia de usuario: Como usuario con sesión activa en el portal, quiero cerrar sesión mediante
 * la opción del menú superior para destruir mis tokens de acceso y asegurar mi cuenta.
 *
 * Prueba de aceptación: Verificar el cierre de sesión (Single Sign-Out) y retorno a la navegación pública.
 */
public class Iqa1_82LogoutSingleSignOutTest extends BaseTest {

    @Test
    public void usuarioCierraSesionExitosamenteYDestruyeSesion() {
        // ===== PREPARACIÓN =====
        // Iniciar sesión en el portal para disponer del estado autenticado y el menú de usuario.
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
        // Paso 1: Localizar y abrir el menú desplegable de usuario (dropdown).
        By dropdownSelector = By.cssSelector("button[data-dropdown-toggle='dropdown']");
        AuthPadronUiHelper.clickWhenReady(driver, wait, dropdownSelector);

        // Paso 2: Hacer clic en la opción de cerrar sesión (Logout) en el menú.
        By logoutSelector = By.xpath("//div[contains(@class,'dropdown-menu')]//a[contains(normalize-space(),'Logout') or contains(normalize-space(),'Cerrar')]");
        AuthPadronUiHelper.clickWhenReady(driver, wait, logoutSelector);

        // Paso 3: Esperar a que la redirección del cierre de sesión complete y retorne a la vista inicial.
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.linkText("Iniciar Sesión")),
                ExpectedConditions.not(ExpectedConditions.urlContains("/perfil/")),
                ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("button[data-dropdown-toggle='dropdown']"))
        ));
        AuthPadronUiHelper.esperarOverlay(driver, wait);

        // ===== VERIFICACIÓN / ASSERT =====
        // Verificar que el menú desplegable del perfil ya no esté visible o activo tras el logout.
        List<WebElement> dropdownMenu = driver.findElements(By.cssSelector(".dropdown-menu:not([style*='display: none'])"));
        soft.assertTrue(dropdownMenu.isEmpty() || !dropdownMenu.get(0).isDisplayed(),
                "El menú de sesión activa debía cerrarse tras completar el logout.");

        // Verificar que en la barra superior vuelva a estar disponible el acceso a 'Iniciar Sesión'.
        String textoNavegacion = driver.findElement(By.tagName("body")).getText();
        boolean opcionLoginDisponible = textoNavegacion.contains("Iniciar Sesión")
                || textoNavegacion.contains("Login")
                || !driver.findElements(By.linkText("Iniciar Sesión")).isEmpty();

        soft.assertTrue(opcionLoginDisponible,
                "La interfaz pública debía volver a mostrar la opción para iniciar sesión.");

        soft.assertAll();
    }
}
