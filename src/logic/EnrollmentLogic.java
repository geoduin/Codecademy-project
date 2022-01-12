package logic;

import java.util.List;

import database.EnrollmentRepository;
import domain.Course;
import domain.Student;
import domain.Enrollment;

public class EnrollmentLogic {
    private CourseLogic courseLogic;
    private StudentLogic studentLogic;
    private EnrollmentRepository enrollmentRepo;

    public EnrollmentLogic() {
        this.courseLogic = new CourseLogic();
        this.studentLogic = new StudentLogic();
        this.enrollmentRepo = new EnrollmentRepository();
    }

    public List<String> getCourseNames() {
        return this.courseLogic.retrieveCourseNames();
    }

    public void enrollStudentToCourse(Student chosenStudent, String courseName) {
        Course chosenCourse = this.courseLogic.retrieveCourse(courseName);
        this.enrollmentRepo.insert(new Enrollment(chosenStudent, chosenCourse));
    }

    public List<String> retrieveEmailsFromCourse(String courseName) {
        return this.enrollmentRepo.retrieveEmailsFromCourse(courseName);
    }

}