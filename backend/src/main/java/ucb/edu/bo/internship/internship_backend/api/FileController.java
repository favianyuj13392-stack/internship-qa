package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import ucb.edu.bo.internship.internship_backend.bl.MinioBl;
import ucb.edu.bo.internship.internship_backend.dto.ImagenResponseDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.service.MinioService;

@RequestMapping("/api/v1")
@RestController
public class FileController {

    private final MinioBl fileBl;
    private final MinioService minioService;
    public FileController(MinioBl fileBl, MinioService minioService) {
        this.fileBl = fileBl;
        this.minioService = minioService;
    }
    @PostMapping("/files/upload")
    public ResponseDto<ImagenResponseDto> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ResponseDto<ImagenResponseDto> responseDto = new ResponseDto<>();
        try {
            // Obtener la URL completa del request
            String fullUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (queryString != null) {
                fullUrl += "?" + queryString;
            }
            //recortar la url a solo la base quitando el endpoint
            fullUrl = fullUrl.substring(0, fullUrl.indexOf("/api/v1/files/upload"));

            // Puedes hacer algo con la URL si es necesario, por ejemplo, agregarla al responseDto
            System.out.println("Request URL: " + fullUrl);

            responseDto = minioService.uploadFileImage(file,fullUrl);
            return responseDto;
        } catch (Exception e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al subir el archivo");
            return responseDto;
        }
    }

    @GetMapping("/public/files/download/{idFile}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String idFile) {
        try {
            return minioService.downloadFile(idFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
