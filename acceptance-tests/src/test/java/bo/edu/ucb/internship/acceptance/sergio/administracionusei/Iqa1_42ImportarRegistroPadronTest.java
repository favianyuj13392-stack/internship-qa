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
 * Historia de Usuario: Como administrador de USEI, quiero importar un nuevo
 * registro al padrón para que aparezca en la consulta de estudiantes.
 *
 * Prueba de Aceptación / Caso de Prueba TC: IQA-1-42: Importar un registro
 * nuevo al Padrón de Estudiantes.
 *
 * PASO 1. Iniciar sesión como administrador y abrir el Padrón de Estudiantes.
 * PASO 2. Cargar un archivo válido y solicitar la simulación de validación.
 * PASO 3. Confirmar que se detecta un registro nuevo sin errores y aplicar
 *         la importación.
 * PASO 4. Buscar el registro importado en el padrón.
 *
 * Resultado Esperado: La importación se confirma y el registro aparece una
 * sola vez en el padrón.
 *
 * Para ejecutar: mvn -f acceptance-tests/pom.xml test -Dtest=Iqa1_42ImportarRegistroPadronTest
 *************************************************************************** */

public class Iqa1_42ImportarRegistroPadronTest extends AdminAcceptanceTestSupport {

    @Test
    public void importarRegistroNuevoAlPadron() throws IOException {
        // Preparación de la prueba
        String carrera = requiredEnvironment("QA_PADRON_CARRERA");
        PadronCsvFixture.GeneratedFile archivo =
                PadronCsvFixture.validRecord(downloadDirectory, carrera);
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
        By cantidadNuevos = By.xpath(
                "//span[normalize-space()='Nuevos']/preceding-sibling::span[1]"
        );
        By cantidadErrores = By.xpath(
                "//span[normalize-space()='Errores']/preceding-sibling::span[1]"
        );
        Assert.assertEquals(uiWait.until(
                ExpectedConditions.visibilityOfElementLocated(cantidadNuevos)
        ).getText().trim(), "1");
        Assert.assertEquals(driver.findElement(cantidadErrores).getText().trim(), "0");

        By botonAplicar = By.xpath(
                "//button[normalize-space()='Confirmar y Aplicar Importación']"
        );
        uiWait.until(ExpectedConditions.elementToBeClickable(botonAplicar)).click();

        WebElement confirmacion = uiWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-title"))
        );
        Assert.assertTrue(confirmacion.getText().contains("Importación Exitosa"));
        driver.findElement(By.cssSelector("button.swal2-confirm")).click();

        WebElement buscador = uiWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[placeholder^='Buscar por correo']")
                )
        );
        buscador.sendKeys(archivo.email(), Keys.ENTER);

        uiWait.until(browser -> filasConCorreo(archivo.email()).size() == 1);
        Assert.assertEquals(filasConCorreo(archivo.email()).size(), 1);
    }

    private List<WebElement> filasConCorreo(String correo) {
        return driver.findElements(By.cssSelector("tbody tr")).stream()
                .filter(fila -> fila.getText().contains(correo))
                .toList();
    }
}
