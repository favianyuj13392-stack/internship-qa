package ucb.edu.bo.internship.internship_backend.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dto.ImagenResponseDto;
import ucb.edu.bo.internship.internship_backend.dto.NewFileDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public NewFileDto uploadFile(MultipartFile file, String bucket) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw new AssertionError();
        String uniqueFilename = generateUniqueFilename(originalFilename);
        uniqueFilename = uniqueFilename.replace(" ", "_");
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucket)
                .object(uniqueFilename)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
        return new NewFileDto(uniqueFilename, file.getContentType(), bucket);
    }

    public String getFile(String bucket, String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs
                .builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(fileName)
                .build());
    }
    public ResponseDto<ImagenResponseDto> uploadFileImage(MultipartFile file, String fullUrl)
    {
        ResponseDto<ImagenResponseDto> responseDto;
        if(checkFile(file.getContentType(), List.of("image/jpeg", "image/png", "image/jpg")))
        {
            responseDto = saveFile(file,fullUrl);
        }
        else {
            responseDto = new ResponseDto<>();
            responseDto.setCode("400");
            responseDto.setErrorMessage("El archivo no es una imagen de formato válido");
        }
        return responseDto;
    }

    private boolean checkFile(String contentType, List<String> validTypes)
    {
        return validTypes.contains(contentType);
    }
    private ResponseDto<ImagenResponseDto> saveFile(MultipartFile file, String fullUrl)
    {
        ResponseDto<ImagenResponseDto> responseDto = new ResponseDto<>();
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) throw new AssertionError();
            String uniqueFilename = generateUniqueFilename(originalFilename);
            uniqueFilename = uniqueFilename.replace(" ", "_");
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("internship-images")
                            .object(uniqueFilename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );

            //String response = "https://backend-sistemas.serverbb.online/api/v1/public/files/download/" + uniqueFilename;
            //String response = "http://localhost:8085/api/v1/public/files/download/" + uniqueFilename;
            String response = fullUrl + "/api/v1/public/files/download/" + uniqueFilename;
            ImagenResponseDto imagenResponseDto = new ImagenResponseDto(response,uniqueFilename,file.getContentType(),String.valueOf(file.getSize()));
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
            responseDto.setResponse(imagenResponseDto);
        } catch (Exception e) {
            System.out.println(e);
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al subir el archivo");
        }
        return responseDto;
    }
    private String generateUniqueFilename(String originalFilename) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        String timestamp = dateFormat.format(new Date());
        int lastIndex = originalFilename.lastIndexOf(".");
        String nameWithoutExtension = lastIndex != -1 ? originalFilename.substring(0, lastIndex) : originalFilename;
        String extension = lastIndex != -1 ? originalFilename.substring(lastIndex) : "";
        return nameWithoutExtension + "_" + timestamp + extension;
    }
    public ResponseEntity<byte[]> downloadFile(String idFile)
    {
        try {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket("internship-images")
                            .object(idFile)
                            .build()
            );
            GetObjectResponse fileContent = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("internship-images")
                            .object(idFile)
                            .build()
            );
            byte[] fileBytes = IOUtils.toByteArray(fileContent);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + stat.object() + "\"")
                    .body(fileBytes);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

