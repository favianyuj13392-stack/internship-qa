package bo.edu.ucb.internship.acceptance.mauricio.portalestudiante;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.clickWhenReady;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.crearArchivoTemporalNoPdf;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.esperarOverlayDeCarga;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.irAPerfilEstudiante;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.loginComoEstudiante;
import static bo.edu.ucb.internship.acceptance.mauricio.portalestudiante.PortalEstudianteUiHelper.requiredCredential;

public class RechazoArchivoNoPdfTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final By AGREGAR_CURRICULUM_LINK = By.xpath("//a[normalize-space()='Agregar curriculums']");
    private static final By FILE_INPUT_PDF = By.cssSelector("#myModalCurriculum input[type='file']");
    private static final By TITULO_PDF_MODAL = By.xpath("//h5[normalize-space()='Título del PDF']");
    private static final By GUARDAR_BUTTON = By.xpath("//button[normalize-space()='Guardar']");

    @Test
    public void rechazaArchivoQueNoEsPdf() throws IOException {
        // ID de TestLink: IQA-1-4
        // Nota: BaseTest/Selenium seleccionan el archivo escribiendo la ruta directamente en el
        // <input type="file">, sin pasar por el diálogo nativo del SO (que sí filtraría por la
        // extensión del atributo accept="application/pdf"). Esto reproduce el caso real de que
        // alguien elija "todos los archivos" en ese diálogo o arrastre un archivo renombrado:
        // en ese escenario la validación debe hacerla la app (cliente o servidor), no el SO.

        // ===== Preparación =====
        String email = requiredCredential("qa.student.email", "QA_STUDENT_EMAIL");
        String password = requiredCredential("qa.student.password", "QA_STUDENT_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        String nombreArchivo = "IQA-1-4_" + System.currentTimeMillis() + ".txt";
        Path archivoNoPdf = crearArchivoTemporalNoPdf(nombreArchivo);

        loginComoEstudiante(driver, wait, appBaseUrl(), email, password);
        irAPerfilEstudiante(driver, wait);

        // ===== Lógica =====
        clickWhenReady(driver, wait, AGREGAR_CURRICULUM_LINK);
        wait.until(ExpectedConditions.presenceOfElementLocated(FILE_INPUT_PDF))
            .sendKeys(archivoNoPdf.toAbsolutePath().toString());
        wait.until(ExpectedConditions.visibilityOfElementLocated(TITULO_PDF_MODAL));
        clickWhenReady(driver, wait, GUARDAR_BUTTON);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(TITULO_PDF_MODAL));
        esperarOverlayDeCarga(driver, wait);

        // ===== Verificación / Assert =====
        boolean apareceEnLista = !driver
            .findElements(By.xpath("//span[contains(text(),'" + nombreArchivo + "')]"))
            .isEmpty();
        Assert.assertFalse(apareceEnLista,
            "Un archivo que no es PDF (.txt) no debería aparecer en el listado de currículums. "
                + "Si este assert falla, confirma el defecto ya documentado BUG-M3-002: ni el frontend "
                + "(el <input accept=\"application/pdf\"> no valida nada por sí mismo) ni el backend "
                + "(agregarCurriculum()/MinioBl.uploadFile() en EstudianteBl.java no comprueban extensión "
                + "ni tipo de archivo) rechazan un archivo que no es PDF.");
    }
}
