package bo.edu.ucb.internship.acceptance.sergio.administracionusei;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/** Builds disposable CSV inputs for the Padrón flows without fixed personal data. */
final class PadronCsvFixture {

    private static final String HEADER =
            "CORREO INSTITUCIONAL,NOMBRES,APELLIDOS,CARRERA,ESTADO,CODIGO ESTUDIANTE,ANIO INGRESO";

    private PadronCsvFixture() {
    }

    static GeneratedFile validRecord(Path directory, String career) throws IOException {
        String token = token();
        String email = "qa.atdd." + token + "@ucb.edu.bo";
        String row = csv(email) + "," + csv("QA ATDD") + "," + csv("Registro " + token)
                + "," + csv(career) + ",ACTIVO," + csv("ATDD" + token.substring(0, 8)) + ",2026";
        Path file = directory.resolve("padron-valido-" + token + ".csv");
        Files.writeString(file, HEADER + System.lineSeparator() + row + System.lineSeparator(),
                StandardCharsets.UTF_8);
        return new GeneratedFile(file, email);
    }

    static GeneratedFile validAndInvalidRows(Path directory, String career) throws IOException {
        String token = token();
        String email = "qa.atdd.valid." + token + "@ucb.edu.bo";
        String validRow = csv(email) + "," + csv("QA ATDD") + "," + csv("Registro " + token)
                + "," + csv(career) + ",ACTIVO," + csv("ATDD" + token.substring(0, 8)) + ",2026";
        String invalidRow = csv("qa.atdd.invalid." + token + "@ucb.edu.bo")
                + "," + csv("QA ATDD") + ",," + csv(career) + ",ACTIVO,"
                + csv("BAD" + token.substring(0, 8)) + ",2026";

        Path file = directory.resolve("padron-con-error-" + token + ".csv");
        Files.writeString(file,
                HEADER + System.lineSeparator() + validRow + System.lineSeparator()
                        + invalidRow + System.lineSeparator(),
                StandardCharsets.UTF_8);
        return new GeneratedFile(file, email);
    }

    private static String token() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    record GeneratedFile(Path path, String email) {
    }
}
