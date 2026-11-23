package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utilidades de UI compartidas por los casos de Portal Empresa (IQA-1-61 a IQA-1-75).
 * No extiende BaseTest ni lo modifica: recibe driver/wait por parametro.
 *
 * Toda la interaccion es por la interfaz (Chrome -> Vue -> Spring Boot). No se invocan
 * endpoints, ni Axios/fetch, ni se consulta la base de datos.
 */
final class PortalEmpresaUiHelper {

    static final By LOADING_OVERLAY = By.cssSelector(".vl-overlay");
    static final By SWAL_POPUP = By.cssSelector(".swal2-popup");
    static final By SWAL_HTML = By.cssSelector("#swal2-html-container");
    static final By SWAL_TITLE = By.cssSelector("#swal2-title");
    static final By SWAL_CONFIRM = By.cssSelector("button.swal2-confirm");

    private static final By LOGIN_LINK = By.linkText("Iniciar Sesión");
    private static final By KC_USERNAME = By.id("username");
    private static final By KC_PASSWORD = By.id("password");
    private static final By KC_LOGIN = By.id("kc-login");

    private PortalEmpresaUiHelper() {
    }

    /**
     * Inicia sesion como empresa usando el formulario de Keycloak (interfaz real), no la API.
     */
    static void loginComoEmpresa(WebDriver driver, WebDriverWait wait, String appBaseUrl,
                                 String email, String password) {
        driver.get(appBaseUrl);
        clickWhenReady(driver, wait, LOGIN_LINK);

        wait.until(ExpectedConditions.visibilityOfElementLocated(KC_USERNAME)).sendKeys(email);
        driver.findElement(KC_PASSWORD).sendKeys(password);
        driver.findElement(KC_LOGIN).click();

        // Al terminar el login, Keycloak redirige de vuelta a la app (fuera de /realms/).
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/realms/")));
        esperarOverlayDeCarga(driver, wait);
    }

    /**
     * Navega a una ruta protegida de empresa por URL. Tras el login la sesion ya esta activa,
     * asi que el router permite el acceso directo (mas estable que recorrer el menu).
     */
    static void irARuta(WebDriver driver, WebDriverWait wait, String appBaseUrl, String ruta) {
        driver.get(appBaseUrl + ruta);
        esperarOverlayDeCarga(driver, wait);
    }

    static void esperarOverlayDeCarga(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING_OVERLAY));
    }

    /** Texto visible del popup de SweetAlert2 (titulo + cuerpo). */
    static String textoDelSweetAlert(WebDriver driver, WebDriverWait wait) {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(SWAL_POPUP));
        return popup.getText();
    }

    /**
     * Hace clic en el elemento ubicado por {@code locator}, reubicandolo en cada intento.
     * Vue puede re-renderizar el DOM justo cuando el elemento queda "clickable", dejando la
     * referencia obsoleta (StaleElementReferenceException) o momentaneamente tapada
     * (ElementClickInterceptedException). Centra el elemento antes de hacer clic y, si Selenium
     * no lo considera interactuable, despacha el clic por JavaScript.
     */
    static void clickWhenReady(WebDriver driver, WebDriverWait wait, By locator) {
        wait.until(d -> {
            try {
                WebElement elemento = d.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) d;
                js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", elemento);
                try {
                    elemento.click();
                } catch (ElementNotInteractableException noInteractuable) {
                    js.executeScript("arguments[0].click();", elemento);
                }
                return true;
            } catch (StaleElementReferenceException | ElementClickInterceptedException retry) {
                return false;
            }
        });
    }

    /**
     * Lee una credencial de QA desde una propiedad del sistema o una variable de entorno.
     * No se guardan credenciales en el repositorio: se pasan al ejecutar Maven con -D o por env.
     */
    static String requiredCredential(String systemProperty, String envVar) {
        String value = resolveProperty(systemProperty, envVar);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "Falta la credencial de empresa QA. Pasa -D" + systemProperty + "=<valor> al ejecutar Maven, "
                    + "o define la variable de entorno " + envVar + " antes de correr la prueba.");
        }
        return value;
    }

    /**
     * Lee un dato de QA obligatorio (por ejemplo, el ID de una pasantia preparada para el caso).
     */
    static String requiredProperty(String systemProperty, String envVar, String descripcion) {
        String value = resolveProperty(systemProperty, envVar);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                descripcion + ". Pasa -D" + systemProperty + "=<valor> al ejecutar Maven, "
                    + "o define la variable de entorno " + envVar + ".");
        }
        return value;
    }

    /** Lee una propiedad opcional con un valor por defecto. */
    static String optionalProperty(String systemProperty, String envVar, String porDefecto) {
        String value = resolveProperty(systemProperty, envVar);
        return (value == null || value.isBlank()) ? porDefecto : value;
    }

    private static String resolveProperty(String systemProperty, String envVar) {
        String value = System.getProperty(systemProperty);
        if (value == null || value.isBlank()) {
            value = System.getenv(envVar);
        }
        return value;
    }
}
