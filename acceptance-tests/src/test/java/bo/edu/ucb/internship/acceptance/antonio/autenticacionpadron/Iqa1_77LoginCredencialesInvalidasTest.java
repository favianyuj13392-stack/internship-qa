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
 * ID de TestLink: IQA-1-77
 *
 * Historia de usuario: Como usuario del sistema, al ingresar credenciales inválidas en Keycloak,
 * el sistema debe rechazar el acceso y mostrar un mensaje de error sin permitir el ingreso al portal.
 *
 * Prueba de aceptación: Verificar el rechazo de autenticación con credenciales erróneas en la interfaz.
 */
public class Iqa1_77LoginCredencialesInvalidasTest extends BaseTest {

    @Test
    public void ingresoConCredencialesInvalidasMuestraErrorYBloqueaAcceso() {
        // ===== PREPARACIÓN =====
        // Abrir la aplicación base y preparar las esperas explícitas de la interfaz.
        driver.get(appBaseUrl());
        WebDriverWait wait = AuthPadronUiHelper.esperar(driver);
        SoftAssert soft = new SoftAssert();

        String usuarioInvalido = "usuario.inexistente.qa@ucb.edu.bo";
        String passwordErroneo = "ContrasenaTotalmenteInvalida999#";

        // ===== LÓGICA =====
        // Paso 1: Hacer clic en 'Iniciar Sesión' de la barra superior para abrir la pantalla institucional de Keycloak.
        AuthPadronUiHelper.clickWhenReady(driver, wait, By.linkText("Iniciar Sesión"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        // Paso 2: Introducir credenciales no registradas o erróneas y presionar el botón de acceso.
        AuthPadronUiHelper.iniciarSesionKeycloak(driver, wait, usuarioInvalido, passwordErroneo);

        // Paso 3: Esperar que la respuesta de error se procese y se visualice en la pantalla de Keycloak.
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-error")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#input-error")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".kc-feedback-text")),
                ExpectedConditions.visibilityOfElementLocated(By.id("input-error-password"))
        ));

        // ===== VERIFICACIÓN / ASSERT =====
        // Verificar que la navegación no abandonó la pantalla de autenticación de Keycloak.
        soft.assertTrue(driver.getCurrentUrl().contains("/auth/") || driver.getCurrentUrl().contains("/realms/"),
                "El usuario no debía ser redirigido a la aplicación ante credenciales inválidas.");

        // Verificar que el mensaje visible de error sobre credenciales inválidas se encuentre en pantalla.
        String textoPantalla = driver.findElement(By.tagName("body")).getText().toLowerCase();
        boolean tieneElementoError = !driver.findElements(By.cssSelector(".alert-error, #input-error, .kc-feedback-text, #input-error-password, .alert")).isEmpty();
        boolean mensajeVisible = tieneElementoError
                || textoPantalla.contains("invalid")
                || textoPantalla.contains("incorrect")
                || textoPantalla.contains("error")
                || textoPantalla.contains("inválid")
                || textoPantalla.contains("no válid");

        soft.assertTrue(mensajeVisible,
                "Debía mostrarse un mensaje informativo de credenciales incorrectas en la interfaz.");

        // Verificar que no se haya montado el menú desplegable de usuario autenticado.
        List<WebElement> dropdownUsuario = driver.findElements(By.cssSelector("button[data-dropdown-toggle='dropdown']"));
        soft.assertTrue(dropdownUsuario.isEmpty(),
                "No debe existir ningún menú de sesión activa en la interfaz.");

        soft.assertAll();
    }
}
