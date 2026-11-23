package bo.edu.ucb.internship.acceptance.jaime.portalpublico;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * ID de TestLink: IQA-1-23
 *
 * Historia de usuario: como estudiante quiero ver todo el detalle de una pasantía antes de postular.
 *
 * Prueba de aceptación: verificar que el detalle de una pasantía muestra toda su información.
 *
 * Datos de QA: "Pasantía de apoyo 14" (funciones "Apoyar al equipo", requisito "Estudiante activo",
 * beneficios "Certificado de pasantía" y "Capacitación", empresa "Banco Andino").
 *
 * Resultado esperado: se muestran la descripción, las funciones y los requisitos, la empresa, los beneficios
 * y la fecha de incorporación.
 *
 * NOTA: esta prueba documenta el defecto BUG-M2-008 (el detalle no muestra beneficios ni fecha de incorporación).
 * Se usan asserts "suaves" para ver qué datos sí aparecen y cuáles no. La expectativa no se cambia.
 */
public class Iqa1_23DetallePasantiaTest extends BaseTest {

    @Test
    public void detalleDeLaPasantiaMuestraTodaSuInformacion() {
        // ===== PREPARACIÓN =====
        // Iniciar sesión por la interfaz y abrir el catálogo de pasantías.
        AccesoPortal.iniciarSesionYAbrir(driver, appBaseUrl(), "/pasantias");
        WebDriverWait espera = AccesoPortal.esperar(driver);
        SoftAssert verificacion = new SoftAssert();

        // ===== LÓGICA =====
        // Paso 1: hacer clic en el título de una pasantía del catálogo.
        espera.until(ExpectedConditions.elementToBeClickable(By.linkText("Pasantía de apoyo 14"))).click();

        // Paso 2: esperar a que cargue la pantalla de detalle y leer todo el texto visible.
        espera.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(normalize-space(),'Detalles de la pasantia')]")));
        espera.until(d -> d.findElement(By.tagName("body")).getText().contains("Apoyar al equipo"));
        String pantalla = driver.findElement(By.tagName("body")).getText().toLowerCase();

        // ===== VERIFICACIÓN / ASSERT =====
        verificacion.assertTrue(driver.getCurrentUrl().contains("/pasantias/14/detalle"),
                "Debía abrirse el detalle de la pasantía 14");
        // Lo que SÍ debe mostrarse y hoy se muestra:
        verificacion.assertTrue(pantalla.contains("apoyar al equipo"), "Debía mostrar las funciones");
        verificacion.assertTrue(pantalla.contains("estudiante activo"), "Debía mostrar los requisitos");
        verificacion.assertTrue(pantalla.contains("banco andino"), "Debía mostrar la empresa que publica");
        // Lo que el caso espera y hoy NO se muestra (BUG-M2-008):
        verificacion.assertTrue(pantalla.contains("certificado de pasantía"), "Debía mostrar los beneficios");
        verificacion.assertTrue(pantalla.contains("incorporaci"), "Debía mostrar la fecha de incorporación");

        verificacion.assertAll();
    }
}
