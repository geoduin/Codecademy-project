package domain;

import java.time.LocalDate;

public class enrollment {
    private Student student;
    private Course course;
    private LocalDate dateOfEnrollment;

    public enrollment(Student student, Course course, LocalDate date) {
        this.student = student;
        this.course = course;
        this.dateOfEnrollment = date;
    }

    public enrollment(Student student, Course course) {
        this(student, course, LocalDate.now());
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setDateOfEnrollment(LocalDate dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public Student getStudent() {
        return student;
    }

}