package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.ModuleRepository;
import domain.Module;
import domain.Course;
import domain.*;

public class ControlLogic {
    private List<Course> courses;
    private List<Module> modules;
    // private CourseRepository courseRepo;
    private ModuleRepository moduleRepo;

    public ControlLogic() {
        this.courses = new ArrayList<>();
        this.modules = new ArrayList<>();
        this.moduleRepo = new ModuleRepository();
        retrieveData();

    }

    // Fill the domain container lists with instances, retrieved and created in the
    // repository's
    private void retrieveData() {
        this.modules = this.moduleRepo.retrieve().stream().filter(Module.class::isInstance)
                .map(Module.class::cast).collect(Collectors.toList());
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

    public void newModule(Status status, String title, int version, int trackingNumber,
            String description, String contactName, String emailAddress) {

        this.modules.add(new Module(status, title, version, trackingNumber, description, contactName,
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

    // Delete a module, if exists. Returns true if succesfull and aks repo to delete
    // from DB
    public boolean deleteModule(Module module) {
        if (!this.modules.contains(module)) {
            return false;
        } else {
            this.moduleRepo.delete((Object) module);
            return true;
        }
    }

    public void editModule(String email, String contact, String description, String status, int orderNumber,
            Module module) {

        // Alterable information
        module.setEmailAddress(email);
        module.setContactName(contact);
        module.setDescription(description);
        Status enumi;
        if (status.equals("ACTIVE")) {
            enumi = domain.Status.ACTIVE;
        } else if (status.equals("ARCHIVED")) {
            enumi = domain.Status.ARCHIVED;
        } else {
            enumi = domain.Status.CONCEPT;
        }
        module.setStatus(enumi);
        module.setTrackingNumber(orderNumber);

        moduleRepo.update(module);

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

    public boolean moduleAlreadyExistsBasedOn(String title, int version) {
        for (Module module : this.modules) {
            if (module.getTitle().equals(title) && module.getVersion() == version) {
                return true;
            }
        }

        return false;
    }
}
