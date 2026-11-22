package bo.edu.ucb.internship.acceptance.sergio.administracionusei;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/* ***************************************************************************
 * Historia de Usuario: Como administrador de USEI, quiero aprobar una
 * pasantía pendiente para que quede disponible en la lista de pasantías publicadas.
 *
 * Prueba de Aceptación / Caso de Prueba TC: IQA-1-36: Aprobar y publicar
 * una pasantía pendiente.
 *
 * PASO 1. Iniciar sesión como administrador y abrir las solicitudes de pasantías.
 * PASO 2. Buscar una solicitud QA pendiente y abrir su detalle.
 * PASO 3. Seleccionar la opción Aprobar.
 * PASO 4. Confirmar el mensaje y buscar la pasantía en las publicaciones.
 *
 * Resultado Esperado: La aplicación confirma la aprobación y muestra la
 * pasantía en la lista de publicaciones.
 *
 * Para ejecutar: mvn -f acceptance-tests/pom.xml test -Dtest=Iqa1_36AprobarPublicarPasantiaTest
 *************************************************************************** */

public class Iqa1_36AprobarPublicarPasantiaTest extends AdminAcceptanceTestSupport {

    @Test
    public void aprobarYPublicarPasantiaPendiente() {
        // Preparación de la prueba
        String titulo = requiredEnvironment("QA_PASANTIA_PENDIENTE_TITULO");
        String terminoBusqueda = titulo.substring(titulo.indexOf(']') + 1).trim();
        openAdminRoute("/administrador/solicitud/pasantia");

        // Lógica de la prueba
        WebElement buscador = uiWait.until(
                ExpectedConditions.elementToBeClickable(By.id("job-keyword"))
        );
        buscador.click();
        buscador.sendKeys(terminoBusqueda);
        uiWait.until(ExpectedConditions.elementToBeClickable(By.id("search"))).click();

        By enlaceDisponible = By.partialLinkText(titulo);
        WebElement pasantiaPendiente = uiWait.until(
                ExpectedConditions.elementToBeClickable(enlaceDisponible)
        );
        String tituloSeleccionado = pasantiaPendiente.getText().trim();
        By enlacePasantia = By.linkText(tituloSeleccionado);
        try {
            uiWait.until(ExpectedConditions.elementToBeClickable(enlacePasantia)).click();
        } catch (org.openqa.selenium.StaleElementReferenceException ignored) {
            uiWait.until(ExpectedConditions.elementToBeClickable(enlacePasantia)).click();
        }
        uiWait.until(ExpectedConditions.urlContains("/administrador/pasantia/"));

        By botonAprobar = By.xpath("//button[normalize-space()='Aprobar']");
        WebElement botonVisible = uiWait.until(
                ExpectedConditions.elementToBeClickable(botonAprobar)
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", botonVisible
        );
        try {
            uiWait.until(ExpectedConditions.elementToBeClickable(botonAprobar)).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException | org.openqa.selenium.StaleElementReferenceException ignored) {
            uiWait.until(ExpectedConditions.visibilityOfElementLocated(botonAprobar))
                    .sendKeys(Keys.ENTER);
        }

        // Verificación del resultado esperado - Assert
        WebElement mensaje = uiWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-title"))
        );
        Assert.assertTrue(mensaje.getText().contains("Pasantia aprobada"));

        driver.findElement(By.cssSelector("button.swal2-confirm")).click();
        openAdminRoute("/administrador/pasantia");

        WebElement buscadorPublicado = uiWait.until(
                ExpectedConditions.elementToBeClickable(By.id("job-keyword"))
        );
        buscadorPublicado.click();
        buscadorPublicado.sendKeys(terminoBusqueda);
        uiWait.until(ExpectedConditions.elementToBeClickable(By.id("search"))).click();

        Assert.assertTrue(uiWait.until(browser -> browser.findElements(enlacePasantia).stream()
                        .anyMatch(element -> {
                            try {
                                return element.isDisplayed();
                            } catch (org.openqa.selenium.StaleElementReferenceException ignored) {
                                return false;
                            }
                        })),
                "La pasantía aprobada debe aparecer en la lista de pasantías publicadas.");
    }
}
