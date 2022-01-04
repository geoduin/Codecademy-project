package domain;

public class Certificate {
    private String studentName;
    private String nameOfIssuer;
    private int grade;
    
    
    public Certificate(String studentName, String nameOfIssuer, int grade) {
        this.studentName = studentName;
        this.nameOfIssuer = nameOfIssuer;
        this.grade = grade;
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
}
