package bo.edu.ucb.internship.acceptance.sergio.administracionusei;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/* ***************************************************************************
 * Historia de Usuario: Como administrador de USEI, quiero exportar el padrón
 * de estudiantes sin primer acceso para revisar ese reporte en Excel.
 *
 * Prueba de Aceptación / Caso de Prueba TC: IQA-1-41: Exportar el reporte
 * de estudiantes sin primer acceso.
 *
 * PASO 1. Iniciar sesión como administrador y abrir el Padrón de Estudiantes.
 * PASO 2. Seleccionar la opción Exportar campaña.
 * PASO 3. Esperar la descarga del reporte.
 *
 * Resultado Esperado: Se descarga el archivo de Excel esperado y no está vacío.
 *
 * Para ejecutar: mvn -f acceptance-tests/pom.xml test -Dtest=Iqa1_41ExportarPadronSinPrimerAccesoTest
 *************************************************************************** */

public class Iqa1_41ExportarPadronSinPrimerAccesoTest extends AdminAcceptanceTestSupport {

    @Test
    public void exportarEstudiantesSinPrimerAcceso() throws IOException {
        // Preparación de la prueba
        openAdminRoute("/administrador/estudiantes");

        // Lógica de la prueba
        By botonExportar = By.xpath("//button[contains(., 'Exportar campaña')]");
        uiWait.until(driver -> driver.findElement(botonExportar).isDisplayed());
        driver.findElement(botonExportar).click();

        // Verificación del resultado esperado - Assert
        Path archivoEsperado = downloadDirectory.resolve("estudiantes_sin_acceso_usei.xlsx");
        Path archivoDescargado = uiWait
                .withTimeout(Duration.ofSeconds(35))
                .until(browser -> Files.isRegularFile(archivoEsperado) ? archivoEsperado : null);

        Assert.assertTrue(Files.size(archivoDescargado) > 0);
    }
}
