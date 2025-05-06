import java.io.Serializable;

public class Grade implements Serializable {
    private String subject;
    private String grade;

    // Constructor
    public Grade(String subject, String grade) {
        this.subject = subject;
        this.grade = grade;
    }

    // Getters for subject and grade
    public String getSubject() {
        return subject;
    }

    public String getGrade() {
        return grade;
    }

    // Optional: Setters if needed
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
