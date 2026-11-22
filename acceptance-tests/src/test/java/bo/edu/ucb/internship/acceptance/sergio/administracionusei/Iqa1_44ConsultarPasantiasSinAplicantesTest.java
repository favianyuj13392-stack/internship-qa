package bo.edu.ucb.internship.acceptance.sergio.administracionusei;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/* ***************************************************************************
 * Historia de Usuario: Como administrador de USEI, quiero consultar las
 * pasantías sin aplicantes y abrir el detalle de una de ellas.
 *
 * Prueba de Aceptación / Caso de Prueba TC: IQA-1-44: Consultar pasantías
 * sin aplicantes.
 *
 * PASO 1. Iniciar sesión como administrador y abrir la lista de pasantías
 *         sin aplicantes.
 * PASO 2. Buscar una pasantía QA de esa lista.
 * PASO 3. Comprobar que el resultado tiene un enlace a su detalle.
 *
 * Resultado Esperado: La pasantía buscada aparece en la lista y su enlace
 * dirige a la pantalla de detalle.
 *
 * Para ejecutar: mvn -f acceptance-tests/pom.xml test -Dtest=Iqa1_44ConsultarPasantiasSinAplicantesTest
 *************************************************************************** */

public class Iqa1_44ConsultarPasantiasSinAplicantesTest extends AdminAcceptanceTestSupport {

    @Test
    public void consultarPasantiasSinAplicantes() {
        // Preparación de la prueba
        String titulo = requiredEnvironment("QA_PASANTIA_SIN_APLICANTES_TITULO");
        String terminoBusqueda = titulo.substring(titulo.indexOf(']') + 1).trim();
        openAdminRoute("/administrador/pasantia/sinaplicantes");

        // Lógica de la prueba
        By buscador = By.id("job-keyword");
        WebElement campoBusqueda = uiWait.until(
                ExpectedConditions.elementToBeClickable(buscador)
        );
        campoBusqueda.click();
        campoBusqueda.sendKeys(terminoBusqueda);
        uiWait.until(ExpectedConditions.elementToBeClickable(By.id("search"))).click();

        By enlacePasantia = By.linkText(titulo);

        // Verificación del resultado esperado - Assert
        Assert.assertTrue(uiWait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"), titulo
        )));
        Assert.assertTrue(uiWait.until(ExpectedConditions.attributeContains(
                enlacePasantia, "href", "/administrador/pasantia/"
        )));
    }
}
