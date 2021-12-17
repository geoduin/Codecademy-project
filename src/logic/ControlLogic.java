package logic;

import java.util.ArrayList;
import java.util.List;
import domain.Module;
import domain.Course;
import domain.*;

public class ControlLogic {
    private List<Course> courses;
    private List<Module> modules;
    // private CourseRepository courseRepo;
    // private ModuleRepository moduleRepo;

    public ControlLogic() {
        this.courses = new ArrayList<>();
        this.modules = new ArrayList<>();
    }

    private void retrieveData() {

    }

    public void addModuleToCourse(Module module, Course courses) {
        // TODO document why this method is empty
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void newModule(String publicationDate, Status status, String title, int version, String trackingNumber,
            String description, String contactName, String emailAddress) {

        this.modules.add(new Module(publicationDate, status, title, version, trackingNumber, description, contactName,
                emailAddress));

    }

    // public void newCourse(String name, String subject, Difficulty difficulty,
    // String description) {
    // // TODO document why this method is empty
    // Course courses = new Course(name, subject, difficulty, description);
    // }

    public Course pickCourse(String courseName) {
        Course pickedCourse = null;
        for (Course courses : courses) {
            if (courses.getName().equals(courseName)) {
                pickedCourse = courses;
            }
        }

        return pickedCourse;
    }

    // public Module pickModule() {

    // }

    public void deleteCourse(Course course) {
        // TODO document why this method is empty
        courses.remove(course);
        // Remove command based on nameCourse
    }

    public void deleteModule(Module module) {
        // TODO document why this method is empty
        String nameModule = module.getTitle();
        for (Module mod : modules) {
            if (mod.getTitle().equals(nameModule)) {
                modules.remove(mod);
            }
        }

        // Remove module command, based on nameModule
    }

    public void alterModule(Module module) {
        // TODO document why this method is empty
        String nameModule = module.getTitle();
        // Alterable information
        String alterDescription = module.getDescription();
        // int alterVersion = module.getVersion();
        // int trackNumber = module.getTrackNumber();
        String alterContactName = module.getContactName();
        String alterEmail = module.getEmailAddress();

        // Alter command op basis van nameModule

    }

    public void alterCourse(Course courses) {
        // TODO document why this method is empty
        // String courseName = courses.getName();
        // String alterTopic = courses.getTopic();
        // String alterDescription = courses.getDiscription();
        // String alterDificulty = courses.getDifficulty().toString();
        // this.topic = topic;
        // this.discription = discription;
        // this.difficulty = difficulty;
        // alter command courses based of name Course

        //
    }
}
