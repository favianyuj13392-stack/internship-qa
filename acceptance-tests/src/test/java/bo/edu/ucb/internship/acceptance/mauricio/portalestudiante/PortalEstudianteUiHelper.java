package bo.edu.ucb.internship.acceptance.mauricio.portalestudiante;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utilidades de UI compartidas por los casos de Portal Estudiante (IQA-1-1 a IQA-1-7).
 * No extiende BaseTest ni lo modifica: recibe driver/wait por parámetro.
 */
final class PortalEstudianteUiHelper {

    private static final By LOADING_OVERLAY = By.cssSelector(".vl-overlay");
    private static final By AGREGAR_CURRICULUM_LINK = By.xpath("//a[normalize-space()='Agregar curriculums']");
    private static final By FILE_INPUT_PDF = By.cssSelector("#myModalCurriculum input[type='file']");
    private static final By TITULO_PDF_MODAL = By.xpath("//h5[normalize-space()='Título del PDF']");
    private static final By GUARDAR_BUTTON = By.xpath("//button[normalize-space()='Guardar']");

    private PortalEstudianteUiHelper() {
    }

    static void loginComoEstudiante(WebDriver driver, WebDriverWait wait, String appBaseUrl,
                                     String email, String password) {
        driver.get(appBaseUrl);
        clickWhenReady(driver, wait, By.linkText("Iniciar Sesión"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("kc-login")).click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/realms/")));
        esperarOverlayDeCarga(driver, wait);
    }

    static void irAPerfilEstudiante(WebDriver driver, WebDriverWait wait) {
        clickWhenReady(driver, wait, By.cssSelector("button[data-dropdown-toggle='dropdown']"));
        clickWhenReady(driver, wait, By.linkText("Perfil"));
        wait.until(ExpectedConditions.urlContains("/perfil/estudiante"));
        esperarOverlayDeCarga(driver, wait);
    }

    static void esperarOverlayDeCarga(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING_OVERLAY));
    }

    /**
     * Sube un PDF desde la pantalla de perfil (debe estar ya en /perfil/estudiante) y espera
     * a que el modal de título se cierre, es decir, a que termine la subida.
     */
    static void subirCurriculumPdf(WebDriver driver, WebDriverWait wait, Path archivoPdf) {
        clickWhenReady(driver, wait, AGREGAR_CURRICULUM_LINK);
        wait.until(ExpectedConditions.presenceOfElementLocated(FILE_INPUT_PDF))
            .sendKeys(archivoPdf.toAbsolutePath().toString());
        wait.until(ExpectedConditions.visibilityOfElementLocated(TITULO_PDF_MODAL));
        clickWhenReady(driver, wait, GUARDAR_BUTTON);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(TITULO_PDF_MODAL));
        esperarOverlayDeCarga(driver, wait);
    }

    static Path crearPdfTemporalConTitulo(String nombreArchivo) throws IOException {
        String contenidoPdfMinimo = "%PDF-1.4\n"
            + "1 0 obj<</Type/Catalog/Pages 2 0 R>>endobj\n"
            + "2 0 obj<</Type/Pages/Kids[3 0 R]/Count 1>>endobj\n"
            + "3 0 obj<</Type/Page/Parent 2 0 R/MediaBox[0 0 200 200]>>endobj\n"
            + "xref\n0 4\n0000000000 65535 f \n"
            + "trailer<</Size 4/Root 1 0 R>>\n"
            + "startxref\n0\n%%EOF";
        Path dir = Files.createTempDirectory("iqa-portal-estudiante-");
        Path archivo = dir.resolve(nombreArchivo);
        Files.write(archivo, contenidoPdfMinimo.getBytes(StandardCharsets.ISO_8859_1));
        return archivo;
    }

    static Path crearArchivoTemporalNoPdf(String nombreArchivo) throws IOException {
        Path dir = Files.createTempDirectory("iqa-portal-estudiante-");
        Path archivo = dir.resolve(nombreArchivo);
        Files.writeString(archivo, "Este es un archivo de texto plano, no un PDF.", StandardCharsets.UTF_8);
        return archivo;
    }

    /**
     * Hace clic en el elemento ubicado por {@code locator}, reubicándolo en cada intento.
     * Vue puede re-renderizar el DOM justo cuando el elemento queda "clickable", dejando la
     * referencia obsoleta (StaleElementReferenceException) o momentáneamente tapada
     * (ElementClickInterceptedException); WebDriverWait ya ignora NoSuchElementException por
     * defecto, así que esto también cubre el hueco entre que un elemento desaparece y aparece
     * el siguiente (por ejemplo, entre dos SweetAlert2 consecutivos).
     */
    static void clickWhenReady(WebDriver driver, WebDriverWait wait, By locator) {
        wait.until(d -> {
            try {
                WebElement elemento = d.findElement(locator);
                // Centra el elemento en el viewport antes de hacer clic: en páginas largas (por
                // ejemplo, EditarPerfilEstudiante.vue) el botón de interés puede quedar, tras el
                // auto-scroll por defecto de Selenium, justo detrás de botones flotantes de
                // posición fija (el switcher de tema, el botón "volver arriba"), provocando
                // ElementClickInterceptedException en todos los reintentos.
                JavascriptExecutor js = (JavascriptExecutor) d;
                js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", elemento);
                try {
                    elemento.click();
                } catch (ElementNotInteractableException noInteractuable) {
                    // Algunos botones (p. ej. el "x" de eliminar currículum, posicionado en
                    // absolute dentro de un contenedor relative) no pasan la comprobación de
                    // interactividad de Selenium aunque son perfectamente clicables para un
                    // usuario real; en ese caso se despacha el clic directamente por JS.
                    js.executeScript("arguments[0].click();", elemento);
                }
                return true;
            } catch (StaleElementReferenceException | ElementClickInterceptedException retry) {
                return false;
            }
        });
    }

    /**
     * Hace clic en el botón "Guardar" del formulario de edición de perfil resolviéndolo,
     * centrándolo y haciendo clic en una sola ejecución de JavaScript por intento, en vez de
     * usar un WebElement de Selenium. EditarPerfilEstudiante.vue es una página muy larga y el
     * botón queda al final de todo, lo que combinado con el auto-scroll y las comprobaciones de
     * superposición de Selenium resultó poco confiable en la práctica.
     */
    static void clickGuardarEnFormulario(WebDriver driver, WebDriverWait wait) {
        String script = "var boton = document.evaluate(\"//button[normalize-space()='Guardar']\", "
            + "document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
            + "if (!boton) { return false; }"
            + "boton.scrollIntoView({block: 'center'});"
            + "boton.click();"
            + "return true;";
        wait.until(d -> Boolean.TRUE.equals(((JavascriptExecutor) d).executeScript(script)));
    }

    static String requiredCredential(String systemProperty, String envVar) {
        String value = System.getProperty(systemProperty);
        if (value == null || value.isBlank()) {
            value = System.getenv(envVar);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "Falta la credencial de estudiante QA. Pasa -D" + systemProperty + "=<valor> al ejecutar Maven, "
                    + "o define la variable de entorno " + envVar + " antes de correr la prueba.");
        }
        return value;
    }
}
