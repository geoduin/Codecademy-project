package logic;

import java.util.List;

import database.enrollRepository;
import domain.Course;
import domain.Student;
import domain.enrollment;

public class enrollLogic {
    private CourseLogic courseLogic;
    private StudentLogic studentLogic;
    private enrollRepository enrollmentRepo;

    public enrollLogic() {
        this.courseLogic = new CourseLogic();
        this.studentLogic = new StudentLogic();
        this.enrollmentRepo = new enrollRepository();
    }

    public List<String> getCourseNames() {
        return this.courseLogic.retrieveCourseNames();
    }

    public void enrollStudentToCourse(Student chosenStudent, String courseName) {
        Course chosenCourse = this.courseLogic.retrieveCourse(courseName);
        this.enrollmentRepo.insert(new enrollment(chosenStudent, chosenCourse));
    }

}