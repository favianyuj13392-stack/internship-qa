package bo.edu.ucb.internship.acceptance.mauricio.portalestudiante;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.clickWhenReady;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.crearPdfTemporalConTitulo;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.esperarOverlayDeCarga;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.irAPerfilEstudiante;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.loginComoEstudiante;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.requiredCredential;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.subirCurriculumPdf;

public class EliminarCurriculumSinPostulacionesTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final By SWAL_CONFIRM_BUTTON = By.cssSelector(".swal2-confirm");

    @Test
    public void eliminaUnCurriculumPropioSinPostulaciones() throws IOException {
        // ID de TestLink: IQA-1-7

        // ===== Preparación =====
        String email = requiredCredential("qa.student.email", "QA_STUDENT_EMAIL");
        String password = requiredCredential("qa.student.password", "QA_STUDENT_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        String tituloUnico = "IQA-1-7_" + System.currentTimeMillis() + ".pdf";
        Path pdfFixture = crearPdfTemporalConTitulo(tituloUnico);

        loginComoEstudiante(driver, wait, appBaseUrl(), email, password);
        irAPerfilEstudiante(driver, wait);
        // El propio test sube el CV que va a eliminar, para no depender de datos ya existentes
        // ni interferir con otros currículums del estudiante semilla.
        subirCurriculumPdf(driver, wait, pdfFixture);

        By spanTitulo = By.xpath("//span[contains(text(),'" + tituloUnico + "')]");
        Assert.assertFalse(driver.findElements(spanTitulo).isEmpty(),
            "Precondición: el CV recién subido debería existir antes de intentar eliminarlo");

        // ===== Lógica =====
        By botonEliminar = By.xpath(
            "//span[contains(text(),'" + tituloUnico + "')]/ancestor::li[1]//button[normalize-space()='x']");
        clickWhenReady(driver, wait, botonEliminar);
        // Confirma el SweetAlert "¿Estás seguro de eliminar este Curriculum?"
        clickWhenReady(driver, wait, SWAL_CONFIRM_BUTTON);
        // Cierra el SweetAlert de éxito "Eliminada!" (no tiene auto-cierre por timer)
        clickWhenReady(driver, wait, SWAL_CONFIRM_BUTTON);
        esperarOverlayDeCarga(driver, wait);

        // ===== Verificación / Assert =====
        boolean siguePresente = !driver.findElements(spanTitulo).isEmpty();
        Assert.assertFalse(siguePresente,
            "El currículum eliminado ('" + tituloUnico + "') no debería seguir apareciendo en el listado");
    }
}
