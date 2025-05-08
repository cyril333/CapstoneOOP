import java.util.List;
import java.util.ArrayList;

public class Student extends Person {
    private List<Grade> grades;


    public Student(String name, String id) {
        super(name, id);
        grades = new ArrayList<>();
    }


    public List<Grade> getGrades() {
        return grades;
    }

    public void addGrade(String subject, String grade) {
        grades.add(new Grade(subject, grade));
    }
}
