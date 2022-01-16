package logic;

import java.util.List;

import database.EnrollmentRepository;
import domain.Student;
import domain.Enrollment;
//This class is responsible for handling information/update/insert requests relating to enrollments.
public class EnrollmentLogic {
    private CourseLogic courseLogic;
    private EnrollmentRepository enrollmentRepo;

    public EnrollmentLogic() {
        this.courseLogic = new CourseLogic();
        this.enrollmentRepo = new EnrollmentRepository();
    }

    // Retrieves all course names from the database
    public List<String> getCourseNames() {
        return this.courseLogic.retrieveCourseNames();
    }

    // Enrolls the student provided as parameter into the course that has the same course name as the given parameter
    public void enrollStudentToCourse(Student chosenStudent, String courseName) {
        this.enrollmentRepo.insert(new Enrollment(chosenStudent.getEmail(), courseName));
    }

    // Retrieves emails from course that has the same name as the given parameter
    public List<String> retrieveEmailsFromCourse(String courseName) {
        return this.enrollmentRepo.retrieveEmailsFromCourse(courseName);
    }

    // Retrieve a list of enrollments for a given student, identified by his mail
    // address
    public List<Enrollment> getsEnrollmentsOfStudent(String studentEmail) {
        return this.enrollmentRepo.retrieveEnrollmentsOfStudent(studentEmail);
    }

    // Deletes enrollment from the database that is provided as a parameter
    public void deleteEnrollment(Enrollment enrollment) {
        this.enrollmentRepo.delete(enrollment);
    }

    // Relay between GUI and Repo to receive all enrollments that are eligible for a
    // certificate. How and why needs an above average amount of explanation, so
    // please refer to the report of PAP4 Codecademy project group
    public List<Enrollment> getEnrollmentsEligibleForCertificate() {
        return this.enrollmentRepo.retrieveCertificateEligibleEnrollments();
    }
}