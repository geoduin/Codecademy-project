package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Course
 */
public class Course {
    private String name;
    private String topic;
    private String description;
    private Difficulty difficulty;
    private List<Module> modules;
    private List<Course> recommendedCourses;

    public Course(String name, String topic, String description, Difficulty difficulty) {
        this.name = name;
        this.topic = topic;
        this.description = description;
        this.difficulty = difficulty;
        this.modules = new ArrayList<>();
        this.recommendedCourses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Course> getRecommendedCourses() {
        return this.recommendedCourses;
    }

    public void setRecommendedCourses(List<Course> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("Name: %s, Topic: %s, Discription: %s, Difficulty: %s\n%s\n \n%s\n  ", name, topic,
                description, this.difficulty, this.modules, this.recommendedCourses);
    }
}