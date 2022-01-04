package domain;

import java.time.LocalDate;

public class Certificate {
    private int certificateID;
    private int enrollmentID;
    private double grade;
    private String employeeName;
    
    public Certificate(int enrollmentID, double grade, String employeeName) {
        this.enrollmentID = enrollmentID;
        this.grade = grade;
        this.employeeName = employeeName;
    }

    public int getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }

    public int getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(int enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
