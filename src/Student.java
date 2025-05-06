import java.util.List;
import java.util.ArrayList;

public class Student extends Person {
    private List<Grade> grades;

    // Constructor
    public Student(String name, String id) {
        super(name, id);  // Call the constructor of Person
        grades = new ArrayList<>();
    }

    // Getters
    public List<Grade> getGrades() {
        return grades;
    }

    // Add grade
    public void addGrade(String subject, String grade) {
        grades.add(new Grade(subject, grade));
    }
}
