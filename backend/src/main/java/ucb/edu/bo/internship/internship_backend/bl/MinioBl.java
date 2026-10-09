package ucb.edu.bo.internship.internship_backend.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dto.NewFileDto;
import ucb.edu.bo.internship.internship_backend.service.MinioService;

@Service
public class MinioBl {

    private MinioService minioService;

    public MinioBl(MinioService minioService) {
        this.minioService = minioService;
    }

    public NewFileDto uploadFile(MultipartFile file, String bucketName) {
       try{
           return minioService.uploadFile(file, bucketName);
       }catch (Exception e){
           System.out.println(e);
             return null;
       }
    }

    public String getFile(String bucketName, String fileName) {
        try {
            return minioService.getFile(bucketName, fileName);
        } catch (Exception e) {
            return null;
        }
    }

}
