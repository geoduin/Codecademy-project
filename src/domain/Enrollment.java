package domain;

import java.time.LocalDate;

public class Enrollment {
    //
    private Integer id;
    private String studentEmail;
    private String courseName;
    private LocalDate dateOfEnrollment;

    public Enrollment(Integer id, String studentEmail, String courseName, LocalDate date) {
        this.id = id;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
        this.dateOfEnrollment = date;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentEmail() {
        return this.studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getDateOfEnrollment() {
        return this.dateOfEnrollment;
    }

    public void setDateOfEnrollment(LocalDate dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public Enrollment(String studentEmail, String courseName) {
        this(null, studentEmail, courseName, LocalDate.now());
    }

    @Override
    public String toString() {
        return "Student: " + this.studentEmail + ", course: '" + this.courseName + "', enrollment date: "
                + this.dateOfEnrollment.toString();
    }
}