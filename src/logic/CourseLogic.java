package logic;

import java.util.ArrayList;

import database.CourseRepository;
import database.ModuleRepository;
import domain.Course;
import domain.Difficulty;

public class CourseLogic {
    private ArrayList<Course> courses;
    private CourseRepository repository;

    public CourseLogic() {
        this.courses = new ArrayList<>();
        this.repository = new CourseRepository();
    }

    public Course retrieveCourse(String courseName) {
        return repository.retrieveCourseByName(courseName);

    }

    public ArrayList<String> retrieveCourseNames() {
        return this.repository.retrieveAllCourseNames();
    }

    public void newCourse(String name, String topic, String description, Difficulty difficulty,
            int firstModuleOfCourseID) {
        Course course = new Course(name, topic, description, difficulty);
        courses.add(course);
        this.repository.insert(course);

        new ModuleRepository().assignModuleToCourse(name, firstModuleOfCourseID);
    }

    public void deleteCourse(String courseName) {
        this.repository.delete(courseName);
    }
}
