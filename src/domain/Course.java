import java.util.ArrayList;
import java.util.List;

/**
 * Course
 */
public class Course {
    private String name;
    private String topic;
    private String discription;
    private Difficulty difficulty;
    private List<Module> courseModules;
    private List<Course> aanbevolenCourses;

    public Course(String name, String topic, String discription, Difficulty difficulty) {
        this.name = name;
        this.topic = topic;
        this.discription = discription;
        this.difficulty = difficulty;
        this.courseModules = new ArrayList<>();
        this.aanbevolenCourses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public String getDiscription() {
        return discription;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Modules> getCourseModules() {
        return courseModules;
    }

    public List<Course> getAanbevolenCourses() {
        return aanbevolenCourses;
    }

    public void setAanbevolenCourses(List<Course> aanbevolenCourses) {
        this.aanbevolenCourses = aanbevolenCourses;
    }

    public void setCourseModules(List<Modules> courseModules) {
        this.courseModules = courseModules;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("Name: %s, Topic: %s, Discription: %s, Difficulty: %s\n%s\n \n%s\n  ", name, topic,
                discription, this.difficulty, this.courseModules, this.aanbevolenCourses);
    }
}