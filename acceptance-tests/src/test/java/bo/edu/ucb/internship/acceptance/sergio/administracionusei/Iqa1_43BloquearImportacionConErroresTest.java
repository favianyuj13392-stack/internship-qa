package bo.edu.ucb.internship.acceptance.sergio.administracionusei;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/* ***************************************************************************
 * Historia de Usuario: Como administrador de USEI, quiero detectar errores
 * en un archivo del padrón y evitar que se aplique una importación inválida.
 *
 * Prueba de Aceptación / Caso de Prueba TC: IQA-1-43: Verificar que la
 * validación detecta errores y evita aplicar un archivo de padrón inválido.
 *
 * PASO 1. Iniciar sesión como administrador y abrir el Padrón de Estudiantes.
 * PASO 2. Cargar un archivo con datos válidos e inválidos y simular la
 *         validación.
 * PASO 3. Revisar el error señalado y comprobar que no está disponible la
 *         opción para aplicar la importación.
 * PASO 4. Cancelar y buscar el registro asociado al dato inválido.
 *
 * Resultado Esperado: La aplicación muestra el error, bloquea la importación
 * y no agrega el registro inválido al padrón.
 *
 * Para ejecutar: mvn -f acceptance-tests/pom.xml test -Dtest=Iqa1_43BloquearImportacionConErroresTest
 *************************************************************************** */

public class Iqa1_43BloquearImportacionConErroresTest extends AdminAcceptanceTestSupport {

    @Test
    public void validarErroresYBloquearImportacionInvalida() throws IOException {
        // Preparación de la prueba
        String carrera = requiredEnvironment("QA_PADRON_CARRERA");
        PadronCsvFixture.GeneratedFile archivo =
                PadronCsvFixture.validAndInvalidRows(downloadDirectory, carrera);
        openAdminRoute("/administrador/estudiantes");

        // Lógica de la prueba
        By botonCargar = By.xpath("//button[contains(., 'Cargar Padrón')]");
        uiWait.until(ExpectedConditions.elementToBeClickable(botonCargar)).click();

        driver.findElement(By.cssSelector("input[type='file']"))
                .sendKeys(archivo.path().toAbsolutePath().toString());

        By botonSimular = By.xpath("//button[normalize-space()='Simular Validación']");
        uiWait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"), archivo.path().getFileName().toString()
        ));
        uiWait.until(ExpectedConditions.elementToBeClickable(botonSimular)).click();

        By tituloSimulacion = By.xpath("//*[normalize-space()='Resultado de la Simulación']");
        uiWait.until(ExpectedConditions.visibilityOfElementLocated(tituloSimulacion));

        // Verificación del resultado esperado - Assert
        By cantidadErrores = By.xpath(
                "//span[normalize-space()='Errores']/preceding-sibling::span[1]"
        );
        Assert.assertEquals(uiWait.until(
                ExpectedConditions.visibilityOfElementLocated(cantidadErrores)
        ).getText().trim(), "1");

        By detalleApellido = By.xpath("//*[contains(normalize-space(.), '[Apellidos]')]");
        Assert.assertTrue(driver.findElement(detalleApellido).isDisplayed());

        By botonAplicar = By.xpath(
                "//button[normalize-space()='Confirmar y Aplicar Importación']"
        );
        Assert.assertTrue(driver.findElements(botonAplicar).isEmpty());

        By botonCancelar = By.xpath("//button[normalize-space()='Cancelar']");
        uiWait.until(ExpectedConditions.elementToBeClickable(botonCancelar)).click();

        WebElement buscador = uiWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[placeholder^='Buscar por correo']")
                )
        );
        buscador.sendKeys(archivo.email(), Keys.ENTER);
        uiWait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"), "No se encontraron estudiantes en el padrón"
        ));

        List<WebElement> filasAplicadas = driver.findElements(By.cssSelector("tbody tr")).stream()
                .filter(fila -> fila.getText().contains(archivo.email()))
                .toList();
        Assert.assertTrue(filasAplicadas.isEmpty());
    }
}
