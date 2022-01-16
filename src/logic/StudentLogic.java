package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.EnrollmentRepository;
import database.StudentRepository;
import database.WebcastRepository;
import domain.Gender;
import domain.Module;
import domain.Student;

public class StudentLogic {
    private StudentRepository studentRepo;
    private EnrollmentRepository enrollRepo;
    private List<String> emails;

    public StudentLogic() {
        this.studentRepo = new StudentRepository();
        this.enrollRepo = new EnrollmentRepository();
        this.emails = new ArrayList<>();
    }

    // Creates a new student and sends it to the studentrepository
    public void newStudent(String name, String email, LocalDate date, Gender gender,
            String street, int houseNumber, String postalCode, String country, String city) {

        Student createdStudent = new Student(name, gender, email, date, street, houseNumber, postalCode, country,
                city);
        this.studentRepo.insert(createdStudent);
    }

    // Receives the attributes from the StudentGUI
    public void updateStudent(Student student, String name, LocalDate newDate, Gender gender, String street,
            String city, String country, int houseNr,
            String postalCode) {
        student.setStudentName(name);
        student.setDateOfBirth(newDate);
        student.setGender(gender);
        student.setstreet(street);
        student.setCountry(country);
        student.setCity(city);
        student.setHouseNumber(houseNr);
        student.setPostalCode(postalCode);
        this.studentRepo.update(student);
    }

    // It receives a hashmap from the student repository and returns this hashmap to
    // the StudentManagement view
    public List<String[]> getNameAndEmail() {
        return this.studentRepo.retrieveNameByEmail();
    }

    // This method checks if the email exist and gives a boolean value
    public boolean emailExist(String checkedMail) {
        return this.studentRepo.retrieveAllEmails().contains(checkedMail);
    }

    // This method receives the email string from the combobox and sends it to the
    // student repository
    // It will get the student back and sends it to the Student GUI
    public Student getStudentByEmail(String email) {
        return this.studentRepo.searchForForStudent(email);
    }

    // Based on the student email, it will search for the student, than it will
    // delete this student from the DB
    public void deleteStudentByEmail(String email) {
        Student studentAboutToDelete = this.studentRepo.searchForForStudent(email);
        this.studentRepo.delete(studentAboutToDelete);
    }

    // Relay between GUI and Repo to receive the progression a Student has within
    // Modules. See StudentRepo for more details
    public Map<Module, Integer> receiveModuleProgressForStudent(Student student) {
        return this.studentRepo.retrieveAllModuleProgressOfStudent(student);
    }

    // Relay between GUI and Repo to update the progression a Student has within a
    // Module or webcast (ContentItem). See StudentRepo for more details
    public void updateProgressContentItem(int contentID, Student student, int newAmount) {
        this.studentRepo.updateProgressOfContentItem(contentID, student.getEmail(), newAmount);
    }

    // Overloaded variant for Webcast, where firstly the Webcast ID is retrieved
    public void updateProgressContentItem(String nameOfSelectedWebcast, Student student, int newAmount) {
        updateProgressContentItem(new WebcastRepository().retrieveByTitle(nameOfSelectedWebcast).getID(), student,
                newAmount);
    }

    // Relay between GUI and Repo to receive the progression a Student has within
    // Webcasts. In this case the key is the Webcast title, because contrary to a
    // module, a title for a webcast is always unique
    public Map<String, Integer> receiveWebcastProgressForStudent(Student student) {
        return this.studentRepo.retrieveAllWebcastProgressOfStudent(student.getEmail());
    }

    // Retrieving webcasts were the Student (given as argument) has no relation with
    // yet (Student isn't watching that webcast yet). Using the Student email as key
    // for the repo call
    public List<String> getWebcastsNotYetLinkedWithStudent(Student student) {
        return this.studentRepo.getWebcastTitlesForStudentThatHaveNoProgressRelation(student.getEmail());
    }

    // Make Student start having progress in a Webcast
    public void studentStartsWatching(String webcastName, Student student) {
        this.enrollRepo.insertProgress(student.getEmail(),
                new WebcastRepository().retrieveByTitle(webcastName).getID());
    }

    public List<String> retrieveAllEmails() {
        return this.studentRepo.retrieveAllEmails();
    }

}
