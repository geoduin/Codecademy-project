package logic;

import java.util.List;

import javax.xml.stream.events.EndElement;

import database.EnrollmentRepository;
import domain.Course;
import domain.Student;
import domain.Enrollment;

public class EnrollmentLogic {
    private CourseLogic courseLogic;
    private StudentLogic studentLogic;
    private EnrollmentRepository enrollmentRepo;

    public EnrollmentLogic() {
        this.courseLogic = new CourseLogic();
        this.studentLogic = new StudentLogic();
        this.enrollmentRepo = new EnrollmentRepository();
    }

    public List<String> getCourseNames() {
        return this.courseLogic.retrieveCourseNames();
    }

    public void enrollStudentToCourse(Student chosenStudent, String courseName) {
        this.enrollmentRepo.insert(new Enrollment(chosenStudent.getEmail(), courseName));
    }

    public List<String> retrieveEmailsFromCourse(String courseName) {
        return this.enrollmentRepo.retrieveEmailsFromCourse(courseName);
    }

    // Retrieve a list of enrollments for a given student, identified by his mail
    // address
    public List<Enrollment> getsEnrollmentsOfStudent(String studentEmail) {
        return this.enrollmentRepo.retrieveEnrollmentsOfStudent(studentEmail);
    }

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