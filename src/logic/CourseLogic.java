package logic;

import java.util.ArrayList;
import java.util.List;

import database.CourseRepository;
import database.ModuleRepository;
import domain.Course;
import domain.Difficulty;

/*
*Class responsible for communication between the GUI and the course repository. It *collects all the functionality that can actually change something the courses.  
*/
public class CourseLogic {
    private ArrayList<Course> courses;
    private CourseRepository repository;

    public CourseLogic() {
        this.courses = new ArrayList<>();
        this.repository = new CourseRepository();
    }

    // Relay GUI <> database to receive a course instance based on its name
    public Course retrieveCourse(String courseName) {
        return repository.retrieveCourseByName(courseName);

    }

    // Receive a list of all courses from the database, in instantiated form
    public List<String> retrieveCourseNames() {
        return this.repository.retrieveAllCourseNames();
    }

    // Create a new course and insert into the database
    public void newCourse(String name, String topic, String description, Difficulty difficulty,
            int idOfFirstModuleWithinCourse) {
        Course course = new Course(name, topic, description, difficulty);
        courses.add(course);
        this.repository.insert(course);

        new ModuleRepository().assignModuleToCourse(name, idOfFirstModuleWithinCourse);
    }

    // Delete a course via the repository from the database based on its name
    public boolean deleteCourse(String courseName) {
        return this.repository.delete(courseName);
    }

    // Edits a course via the repository from the database based on its name
    public void editCourse(String topic, String description, Difficulty difficulty, Course course) {
        course.setTopic(topic);
        course.setDescription(description);
        course.setDifficulty(difficulty);
        this.repository.update(course);
    }

    // Using two courses as arguments, for one course setting the other course as
    // recommended course in the database
    public void setRecommendedCourse(String courseName, String recommendedCourseName) {
        this.repository.addRecommendedCourse(courseName, recommendedCourseName);
    }
}
