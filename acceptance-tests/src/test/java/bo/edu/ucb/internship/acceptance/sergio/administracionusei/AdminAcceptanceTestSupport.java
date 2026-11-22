package bo.edu.ucb.internship.acceptance.sergio.administracionusei;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/** Comparte el inicio de sesión QA necesario para las pantallas administrativas. */
abstract class AdminAcceptanceTestSupport extends BaseTest {

    private static final Map<String, String> DEFAULT_QA_VALUES = Map.of(
            "QA_PADRON_CARRERA", "INGENIERÍA DE SISTEMAS",
            "QA_PASANTIA_PENDIENTE_TITULO", "[QA Sergio] Moderación rechazar",
            "QA_PASANTIA_SIN_APLICANTES_TITULO", "[QA Sergio] Oferta sin aplicantes"
    );

    protected WebDriverWait uiWait;

    protected void openAdminRoute(String route) {
        String baseUrl = appBaseUrl().replaceAll("/+$", "");
        uiWait = new WebDriverWait(driver, Duration.ofSeconds(35));

        // Abrir la ruta administrativa en el navegador.
        driver.get(baseUrl + route);

        String initialState = uiWait.until(browser -> {
            if (isVisible(browser, By.id("username"))) {
                return "LOGIN";
            }
            if (browser.getCurrentUrl().startsWith(baseUrl) && hasVisibleHeading(browser)) {
                return "APP";
            }
            return null;
        });

        if ("LOGIN".equals(initialState)) {
            String username = requiredEnvironment("QA_ADMIN_USERNAME");
            String password = requiredEnvironment("QA_ADMIN_PASSWORD");

            uiWait.until(browser -> visibleElement(browser, By.id("username")))
                    .sendKeys(username);
            uiWait.until(browser -> visibleElement(browser, By.id("password")))
                    .sendKeys(password);
            uiWait.until(browser -> visibleElement(browser, By.id("kc-login")))
                    .click();
        }

        try {
            uiWait.until(browser ->
                    browser.getCurrentUrl().startsWith(baseUrl)
                            && browser.getCurrentUrl().contains(route)
                            && hasVisibleHeading(browser)
            );
        } catch (TimeoutException error) {
            String body = driver.findElement(By.tagName("body")).getText().replace('\n', ' ');
            if (body.length() > 500) {
                body = body.substring(0, 500);
            }
            throw new TimeoutException("No se abrió " + route + ". URL actual: "
                    + driver.getCurrentUrl() + "; título: " + driver.getTitle()
                    + "; texto visible: " + body, error);
        }
    }

    protected String requiredEnvironment(String name) {
        String value = environmentValue(name);
        if (value == null || value.isBlank()) {
            value = DEFAULT_QA_VALUES.get(name);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Falta configurar " + name
                            + " en el entorno local de QA. Las credenciales deben permanecer fuera del repositorio."
            );
        }
        return value.trim();
    }

    private boolean hasVisibleHeading(WebDriver browser) {
        return browser.findElements(By.cssSelector("h1, h2, h3")).stream()
                .anyMatch(WebElement::isDisplayed);
    }

    private boolean isVisible(WebDriver browser, By locator) {
        List<WebElement> elements = browser.findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    private WebElement visibleElement(WebDriver browser, By locator) {
        List<WebElement> elements = browser.findElements(locator);
        return elements.isEmpty() || !elements.get(0).isDisplayed() ? null : elements.get(0);
    }
}
