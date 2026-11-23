package bo.edu.ucb.internship.acceptance.alejandro.portalempresa;

import bo.edu.ucb.internship.acceptance.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.irARuta;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.loginComoEmpresa;
import static bo.edu.ucb.internship.acceptance.alejandro.portalempresa.PortalEmpresaUiHelper.requiredCredential;

/**
 * Caso de aceptacion para IQA-1-72: una empresa deberia poder actualizar sus datos
 * institucionales desde la pantalla "Informacion de la empresa".
 *
 * NOTA: se espera que esta prueba FALLE mientras exista el defecto ya reportado BUG-M4-003.
 * La vista /empresa/administrador/informacion es de solo lectura (EditarInformacionEmpresaView.vue
 * no tiene ningun input, textarea ni boton "Guardar"), por lo que no hay forma de editar los datos
 * desde la interfaz. Siguiendo la pauta del entregable, no se maquilla el assert: el fallo
 * demuestra el defecto. Cuando se corrija el bug (formulario editable + boton Guardar), la prueba
 * pasara sin cambios.
 */
public class ActualizarDatosInstitucionalesEmpresaTest extends BaseTest {

    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);
    private static final String RUTA_INFORMACION = "/empresa/administrador/informacion";

    // Controles que deberian existir para poder actualizar los datos de la empresa.
    private static final By CONTROLES_DE_EDICION = By.xpath(
        "//button[normalize-space()='Guardar']"
            + " | //button[contains(normalize-space(),'Guardar cambios')]"
            + " | //a[contains(normalize-space(),'Guardar cambios')]"
            + " | //input[@type='text'] | //textarea");

    @Test
    public void permiteActualizarLosDatosInstitucionalesDeLaEmpresa() {
        // ID de TestLink: IQA-1-72

        // ===== Preparación =====
        String email = requiredCredential("qa.empresa.email", "QA_EMPRESA_EMAIL");
        String password = requiredCredential("qa.empresa.password", "QA_EMPRESA_PASSWORD");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        loginComoEmpresa(driver, wait, appBaseUrl(), email, password);

        // ===== Lógica =====
        irARuta(driver, wait, appBaseUrl(), RUTA_INFORMACION);

        // ===== Verificación / Assert =====
        boolean permiteEditar = !driver.findElements(CONTROLES_DE_EDICION).isEmpty();
        Assert.assertTrue(permiteEditar,
            "La pantalla 'Informacion de la empresa' deberia permitir editar los datos "
                + "institucionales (campos editables y un boton 'Guardar'). "
                + "Si este assert falla, confirma el defecto ya documentado BUG-M4-003: la vista "
                + "EditarInformacionEmpresaView.vue es de solo lectura (no tiene inputs ni boton "
                + "'Guardar'), por lo que la empresa no puede actualizar su telefono, direccion ni "
                + "logotipo desde la interfaz, aunque el backend si expone "
                + "InstitucionBl.actualizarInstitucion.");
    }
}
