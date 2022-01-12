package domain;

public class Certificate {
    private int certificateID;
    private String studentName;
    private String nameOfIssuer;
    private int grade;
    
    
    public Certificate(int certificateID, String studentName, String nameOfIssuer, int grade) {
        this.certificateID = certificateID;
        this.studentName = studentName;
        this.nameOfIssuer = nameOfIssuer;
        this.grade = grade;
    }

    public int getCertificateID() {
        return certificateID;
    }


    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }

    public String getStudentName() {
        return studentName;
    }


    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    @Override
    public String toString() {
        return "CertificateID: " + this.certificateID + ", Student name: " + this.studentName + ", Name of Issuer: " + this.nameOfIssuer + ", Grade: " + this.grade + "\n";
    }
}
