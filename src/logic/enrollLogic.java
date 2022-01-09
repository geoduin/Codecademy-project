package logic;

import java.util.List;

import database.EnrollRepository;
import domain.Course;
import domain.Student;
import domain.Enrollment;

public class EnrollLogic {
    private CourseLogic courseLogic;
    private StudentLogic studentLogic;
    private EnrollRepository enrollmentRepo;

    public EnrollLogic() {
        this.courseLogic = new CourseLogic();
        this.studentLogic = new StudentLogic();
        this.enrollmentRepo = new EnrollRepository();
    }

    public List<String> getCourseNames() {
        return this.courseLogic.retrieveCourseNames();
    }

    public void enrollStudentToCourse(Student chosenStudent, String courseName) {
        Course chosenCourse = this.courseLogic.retrieveCourse(courseName);
        this.enrollmentRepo.insert(new Enrollment(chosenStudent, chosenCourse));
    }

}