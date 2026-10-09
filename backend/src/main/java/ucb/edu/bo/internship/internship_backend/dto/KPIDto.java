package ucb.edu.bo.internship.internship_backend.dto;

public class KPIDto {
    private String title;
    private Long value;

    public KPIDto() {
    }

    public KPIDto(String title, Long value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KPIDto{" +
                "title='" + title + '\'' +
                ", value=" + value +
                '}';
    }
}
