package bo.edu.ucb.internship.acceptance.jaime.portalpublico;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * ID de TestLink: IQA-1-20
 *
 * Historia de usuario: como estudiante quiero filtrar el directorio de empresas por sector para ver solo
 * las empresas que me interesan.
 *
 * Prueba de aceptación: verificar que el directorio de empresas se puede filtrar por sector.
 *
 * Datos de QA: empresas del entorno local: TechBo (Tecnología), Banco Andino (Banca) y TelecomSur (Telecomunicaciones).
 *
 * Resultado esperado: al elegir el sector Tecnología y pulsar Buscar, se excluyen las empresas de otros sectores
 * y queda solo la empresa del sector Tecnología.
 */
public class Iqa1_20FiltroEmpresasPorSectorTest extends BaseTest {

    private static final By EMPRESA_TECHBO = By.xpath("//a[normalize-space()='TechBo']");
    private static final By EMPRESA_BANCO = By.xpath("//a[normalize-space()='Banco Andino']");
    private static final By EMPRESA_TELECOM = By.xpath("//a[normalize-space()='TelecomSur']");

    @Test
    public void filtrarEmpresasPorSectorTecnologiaDejaSoloEmpresasDeTecnologia() {
        // ===== PREPARACIÓN =====
        // Iniciar sesión por la interfaz y abrir el directorio de empresas con todas las empresas visibles.
        AccesoPortal.iniciarSesionYAbrir(driver, appBaseUrl(), "/empresas");
        WebDriverWait espera = AccesoPortal.esperar(driver);
        espera.until(ExpectedConditions.visibilityOfElementLocated(EMPRESA_BANCO));
        assertTrue(driver.findElement(EMPRESA_TECHBO).isDisplayed(), "Antes de filtrar debía verse TechBo");

        // ===== LÓGICA =====
        // Paso 1: abrir el selector de sector.
        driver.findElement(By.cssSelector(".vs__dropdown-toggle")).click();

        // Paso 2: elegir "Tecnología" en la lista desplegable.
        espera.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and normalize-space()='Tecnología']"))).click();

        // Paso 3: pulsar el botón Buscar y esperar a que desaparezcan las empresas de otros sectores.
        driver.findElement(By.id("search")).click();
        espera.until(ExpectedConditions.invisibilityOfElementLocated(EMPRESA_BANCO));

        // ===== VERIFICACIÓN / ASSERT =====
        assertTrue(driver.findElement(EMPRESA_TECHBO).isDisplayed(),
                "La empresa del sector Tecnología (TechBo) debía seguir visible");
        assertEquals(driver.findElements(EMPRESA_BANCO).size(), 0,
                "Banco Andino (Banca) debía excluirse del resultado");
        assertEquals(driver.findElements(EMPRESA_TELECOM).size(), 0,
                "TelecomSur (Telecomunicaciones) debía excluirse del resultado");
    }
}
