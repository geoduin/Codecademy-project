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
    private int nrOfCertificates;

    public Course(String name, String topic, String description, Difficulty difficulty) {
        this.name = name;
        this.topic = topic;
        this.description = description;
        this.difficulty = difficulty;
        this.modules = new ArrayList<>();
        this.recommendedCourses = new ArrayList<>();
        this.nrOfCertificates = 0;
    }

    public void setNrOfCertificates(int number) { 
        this.nrOfCertificates = number;
    }

    public int getNrOfCertificates() { 
        return this.nrOfCertificates;
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

        return "Name: " +  this.name + ", Nr. Of Certificates: " + this.nrOfCertificates;
        
    }
}