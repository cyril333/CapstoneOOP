import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GradeManager implements Serializable {
    private List<Student> students;

    public GradeManager() {
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public boolean removeStudent(String id) {
        Student student = getStudentById(id);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
    }

    public String displayAllStudents() {
        StringBuilder sb = new StringBuilder();

        for (Student student : students) {
            sb.append("Student ID: ").append(student.getId())
                    .append("\nStudent Name: ").append(student.getName())
                    .append("\nGrades:\n");

            for (Grade grade : student.getGrades()) {
                sb.append("  ").append(grade.getSubject()).append(": ").append(grade.getGrade())
                        .append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
