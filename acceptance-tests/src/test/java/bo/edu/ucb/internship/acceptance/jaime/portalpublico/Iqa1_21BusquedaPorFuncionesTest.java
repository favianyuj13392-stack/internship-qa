package bo.edu.ucb.internship.acceptance.jaime.portalpublico;

import bo.edu.ucb.internship.acceptance.BaseTest;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * ID de TestLink: IQA-1-21
 *
 * Historia de usuario: como estudiante quiero buscar pasantías por una palabra clave y encontrar las que la
 * contienen en el título o en las funciones.
 *
 * Prueba de aceptación: verificar que el buscador del catálogo encuentra pasantías por título y por funciones.
 *
 * Datos de QA: "Desarrollador Java" (la palabra está en el título) y varias pasantías con la función
 * "Apoyar al equipo" (la palabra está solo en las funciones).
 *
 * Resultado esperado: la búsqueda funciona con Enter y con el botón Buscar, y también considera las funciones.
 *
 * NOTA: esta prueba documenta defectos conocidos (BUG-M2-007: el buscador solo mira el título; y la tecla Enter
 * recarga la página sin filtrar). Se usan asserts "suaves" para que el reporte liste todas las diferencias.
 * La expectativa no se cambia para forzar un éxito.
 */
public class Iqa1_21BusquedaPorFuncionesTest extends BaseTest {

    private static final By CUADRO_DE_BUSQUEDA = By.id("searchname");
    private static final By BOTON_BUSCAR = By.xpath("//button[normalize-space()='Buscar']");
    private static final By TITULOS = By.cssSelector("a[href*='/pasantias/'][href$='/detalle']");
    private static final By SIN_RESULTADOS = By.xpath("//h1[contains(normalize-space(),'No se encontraron')]");

    @Test
    public void buscarPorTituloYPorFuncionesEnElCatalogo() {
        // ===== PREPARACIÓN =====
        // Iniciar sesión por la interfaz y abrir el catálogo con todas las pasantías cargadas.
        AccesoPortal.iniciarSesionYAbrir(driver, appBaseUrl(), "/pasantias");
        WebDriverWait espera = AccesoPortal.esperar(driver);
        espera.until(d -> !titulos().isEmpty());
        SoftAssert verificacion = new SoftAssert();

        // ===== LÓGICA + VERIFICACIÓN (por paso del caso) =====
        // Pasos 1 y 2: escribir "Java" y presionar Enter. Se espera que la lista se filtre.
        AccesoPortal.escribir(driver.findElement(CUADRO_DE_BUSQUEDA), "Java");
        driver.findElement(CUADRO_DE_BUSQUEDA).sendKeys(Keys.ENTER);
        espera.until(ExpectedConditions.urlContains("search="));
        espera.until(d -> !titulos().isEmpty());
        verificacion.assertTrue(todosContienen(titulos(), "java"),
                "Con Enter debían quedar solo pasantías con 'Java', pero la lista mostró: " + titulos());

        // Paso 3: escribir "Java" otra vez y pulsar el botón Buscar. Se espera solo "Desarrollador Java".
        List<String> antesDeBuscar = titulos();
        AccesoPortal.escribir(driver.findElement(CUADRO_DE_BUSQUEDA), "Java");
        driver.findElement(BOTON_BUSCAR).click();
        esperarCambio(espera, antesDeBuscar);
        verificacion.assertEquals(titulos(), List.of("Desarrollador Java"),
                "Con el botón Buscar debía quedar solo 'Desarrollador Java'");

        // Paso 4: buscar "Apoyar", palabra que está en las funciones y no en el título.
        List<String> antesDeApoyar = titulos();
        AccesoPortal.escribir(driver.findElement(CUADRO_DE_BUSQUEDA), "Apoyar");
        driver.findElement(BOTON_BUSCAR).click();
        esperarCambio(espera, antesDeApoyar);
        verificacion.assertFalse(titulos().isEmpty(),
                "Debían mostrarse las pasantías cuyas funciones contienen 'Apoyar', pero el catálogo mostró el mensaje: "
                        + (haySinResultados() ? "No se encontraron pasantias" : "(ninguno)"));

        verificacion.assertAll();
    }

    /** Títulos visibles de las tarjetas del catálogo. */
    private List<String> titulos() {
        return driver.findElements(TITULOS).stream()
                .map(WebElement::getText)
                .filter(texto -> !texto.isBlank())
                .collect(Collectors.toList());
    }

    private boolean todosContienen(List<String> textos, String palabraEnMinusculas) {
        return !textos.isEmpty() && textos.stream().allMatch(t -> t.toLowerCase().contains(palabraEnMinusculas));
    }

    private boolean haySinResultados() {
        return driver.findElements(SIN_RESULTADOS).stream().anyMatch(WebElement::isDisplayed);
    }

    /** Espera explícita: la pantalla ya respondió cuando aparece el mensaje de sin resultados o cambia la lista. */
    private void esperarCambio(WebDriverWait espera, List<String> anterior) {
        espera.until(d -> haySinResultados() || !titulos().equals(anterior));
    }
}
