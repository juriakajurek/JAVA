public class Currency {
    private int id;
    private String code;
    private String date;
    private String course;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String tytul) {
        this.date = tytul;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String autor) {
        this.course = autor;
    }

    public Currency() {}
    public Currency(int id, String code, String date, String course) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.course = course;
    }
    @Override
    public String toString() {
        return "["+id+"] - "+code+" - "+date+" - "+course;
    }
}