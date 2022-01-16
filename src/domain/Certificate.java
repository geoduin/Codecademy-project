package domain;

public class Certificate {
    private int certificateID;
    private int enrollmentID;
    private String studentName;
    private String nameOfIssuer;
    private int grade;
    private String courseName;
    
    
    public Certificate(int enrollmentID, String nameOfIssuer, int grade) {
        this.enrollmentID = enrollmentID;
        this.nameOfIssuer = nameOfIssuer;
        this.grade = grade;
    }

    public Certificate(int certificateID, String courseName, String studentName, int enrollmentID, String nameOfIssuer, int grade) {
        this.certificateID = certificateID;
        this.courseName = courseName;
        this.studentName = studentName;
        this.enrollmentID = enrollmentID;
        this.nameOfIssuer = nameOfIssuer;
        this.grade = grade;
    }

    public int getEnrollmentID() {
        return enrollmentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getNameOfIssuer() {
        return nameOfIssuer;
    }

    public void setNameOfIssuer(String nameOfIssuer) {
        this.nameOfIssuer = nameOfIssuer;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    

    public int getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }


    @Override
    public String toString() {
        return "Student: " + this.studentName + ", name of issuer: " + this.nameOfIssuer + ", grade: " + this.grade;
    }
}
