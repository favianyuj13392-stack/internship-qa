package bo.edu.ucb.internship.acceptance.mauricio.portalestudiante;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.crearPdfTemporalConTitulo;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.irAPerfilEstudiante;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.loginComoEstudiante;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.requiredCredential;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.subirCurriculumPdf;

public class SubirCurriculumPdfExitosoTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);

    @Test
    public void subeUnPdfYApareceEnElListadoDeCurriculums() throws IOException {
        // ID de TestLink: IQA-1-3

        // ===== Preparación =====
        String email = requiredCredential("qa.student.email", "QA_STUDENT_EMAIL");
        String password = requiredCredential("qa.student.password", "QA_STUDENT_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        String tituloUnico = "IQA-1-3_" + System.currentTimeMillis() + ".pdf";
        Path pdfFixture = crearPdfTemporalConTitulo(tituloUnico);

        loginComoEstudiante(driver, wait, appBaseUrl(), email, password);
        irAPerfilEstudiante(driver, wait);

        // ===== Lógica =====
        subirCurriculumPdf(driver, wait, pdfFixture);

        // ===== Verificación / Assert =====
        boolean apareceEnLista = !driver
            .findElements(By.xpath("//span[contains(text(),'" + tituloUnico + "')]"))
            .isEmpty();
        Assert.assertTrue(apareceEnLista,
            "El PDF subido ('" + tituloUnico + "') debería aparecer en el listado de currículums del estudiante");
    }
}
