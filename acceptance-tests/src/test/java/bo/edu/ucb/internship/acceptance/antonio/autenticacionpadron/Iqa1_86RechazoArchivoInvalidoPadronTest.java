package bo.edu.ucb.internship.acceptance.antonio.autenticacionpadron;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * ID de TestLink: IQA-1-86
 *
 * Historia de usuario: Como administrador responsable de la importación del Padrón Estudiantil,
 * si intento cargar un archivo con extensión no soportada (.txt o corrupto), el sistema debe
 * rechazarlo a nivel de validación y mostrar un mensaje de error sin alterar la base de datos.
 *
 * Prueba de aceptación: Verificar el rechazo y manejo de error visible al subir archivos con formato no soportado.
 */
public class Iqa1_86RechazoArchivoInvalidoPadronTest extends BaseTest {

    @Test
    public void cargaDeArchivoConFormatoInvalidoMuestraAlertaDeError() throws IOException {
        // ===== PREPARACIÓN =====
        // Iniciar sesión con rol ADMIN para acceder a la gestión del Padrón Estudiantil.
        driver.get(appBaseUrl());
        WebDriverWait wait = AuthPadronUiHelper.esperar(driver);
        SoftAssert soft = new SoftAssert();

        String usuarioAdmin = AuthPadronUiHelper.getUsuarioAdmin();
        String passwordAdmin = AuthPadronUiHelper.getPasswordAdmin();

        AuthPadronUiHelper.clickWhenReady(driver, wait, By.linkText("Iniciar Sesión"));
        AuthPadronUiHelper.iniciarSesionKeycloak(driver, wait, usuarioAdmin, passwordAdmin);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/realms/")));
        AuthPadronUiHelper.esperarOverlay(driver, wait);

        // Crear un archivo temporal de texto no soportado (.txt) para la prueba de carga.
        Path archivoInvalido = AuthPadronUiHelper.crearArchivoInvalido(
                "padron_estudiantes_invalido.txt",
                "Este archivo no es un libro de Excel ni un CSV valido para el padron."
        );

        // ===== LÓGICA =====
        // Paso 1: Navegar a la pantalla de gestión del Padrón Estudiantil.
        driver.get(appBaseUrl() + "/administrador/estudiantes");
        AuthPadronUiHelper.esperarOverlay(driver, wait);

        // Paso 2: Abrir el modal de carga masiva de Padrón.
        By botonCargarPadron = By.xpath("//button[contains(.,'Cargar Padrón') or contains(.,'Cargar')]");
        AuthPadronUiHelper.clickWhenReady(driver, wait, botonCargarPadron);

        // Paso 3: Localizar el input de archivo y asociar el archivo con formato no soportado.
        WebElement inputArchivo = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        inputArchivo.sendKeys(archivoInvalido.toAbsolutePath().toString());

        // Paso 4: Ejecutar la acción de validación / simulación.
        By botonSimular = By.xpath("//button[contains(.,'Validar y Simular') or contains(.,'Simular')]");
        AuthPadronUiHelper.clickWhenReady(driver, wait, botonSimular);

        // Paso 5: Esperar que el modal de error o notificación (SweetAlert2 o alerta visible) aparezca.
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-popup")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-error")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-danger")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Error')]"))
        ));

        // ===== VERIFICACIÓN / ASSERT =====
        // Verificar que la interfaz desplegó una alerta indicando el error de validación del archivo.
        List<WebElement> alertaError = driver.findElements(By.cssSelector(".swal2-popup, .swal2-error, .alert-danger"));
        soft.assertFalse(alertaError.isEmpty(),
                "Debía desplegarse una alerta visual informando el rechazo del archivo inválido.");

        String textoAlerta = driver.findElement(By.tagName("body")).getText();
        boolean advertenciaVisible = textoAlerta.contains("Error")
                || textoAlerta.contains("formato")
                || textoAlerta.contains("inválido")
                || textoAlerta.contains("soportado");

        soft.assertTrue(advertenciaVisible,
                "El mensaje en pantalla debía detallar que el archivo o su formato no es válido.");

        soft.assertAll();
    }
}
