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
 * ID de TestLink: IQA-1-76
 *
 * Historia de usuario: Como estudiante autenticado, quiero iniciar sesión en el portal mediante
 * Keycloak SSO para acceder a mis funcionalidades y consultar ofertas de pasantía.
 *
 * Prueba de aceptación: Verificar inicio de sesión exitoso por interfaz y confirmación de sesión activa.
 */
public class Iqa1_76LoginExitosoEstudianteTest extends BaseTest {

    @Test
    public void estudianteIniciaSesionExitosamenteConKeycloak() {
        // ===== PREPARACIÓN =====
        // Abrir la aplicación base y preparar las esperas explícitas de la interfaz.
        driver.get(appBaseUrl());
        WebDriverWait wait = AuthPadronUiHelper.esperar(driver);
        SoftAssert soft = new SoftAssert();

        String usuario = AuthPadronUiHelper.getUsuarioEstudiante();
        String password = AuthPadronUiHelper.getPasswordEstudiante();

        // ===== LÓGICA =====
        // Paso 1: Hacer clic en el enlace 'Iniciar Sesión' de la barra de navegación pública.
        AuthPadronUiHelper.clickWhenReady(driver, wait, By.linkText("Iniciar Sesión"));

        // Paso 2: Esperar el formulario institucional de Keycloak y enviar credenciales válidas.
        AuthPadronUiHelper.iniciarSesionKeycloak(driver, wait, usuario, password);

        // Paso 3: Esperar que Keycloak complete el flujo SSO y redirija a la aplicación Vue.
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/realms/")));
        AuthPadronUiHelper.esperarOverlay(driver, wait);

        // ===== VERIFICACIÓN / ASSERT =====
        // Verificar que la URL actual pertenece al portal de la aplicación y ya no a la pantalla de login.
        soft.assertTrue(driver.getCurrentUrl().startsWith(appBaseUrl()),
                "El navegador debía retornar al dominio base de la aplicación tras autenticarse.");
        soft.assertFalse(driver.getCurrentUrl().contains("/login"),
                "La URL no debería permanecer en /login tras el inicio de sesión exitoso.");

        // Verificar que el menú de usuario autenticado (dropdown) ahora está disponible en la interfaz.
        List<WebElement> dropdownUsuario = driver.findElements(By.cssSelector("button[data-dropdown-toggle='dropdown']"));
        soft.assertFalse(dropdownUsuario.isEmpty(),
                "La interfaz debía mostrar el control desplegable del perfil de usuario autenticado.");

        soft.assertAll();
    }
}
