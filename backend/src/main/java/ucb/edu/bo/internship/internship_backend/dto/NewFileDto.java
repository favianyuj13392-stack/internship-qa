package ucb.edu.bo.internship.internship_backend.dto;

public class NewFileDto {
    String fileName;
    String contentType;
    String bucket;

    public NewFileDto(String fileName, String contentType, String bucket) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.bucket = bucket;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}