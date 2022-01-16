package logic;

import java.util.ArrayList;
import database.CertificateRepository;
import domain.Enrollment;
import domain.Certificate;

public class CertificateLogic {
    private CertificateRepository certificateRepository;

    //This class is responsible for handling information/insert/update requests relating to certificates.

    public CertificateLogic() {
        this.certificateRepository = new CertificateRepository();
    }

    // Creates certificate with provided parameters and inserts it into the database
    public void newCertificate(Enrollment enrollment, String employeeName, int grade) {
        int enrollmentID = enrollment.getId();
        Certificate certificate = new Certificate(enrollmentID, employeeName, grade);
        certificateRepository.insert(certificate);
    }

    // Updates/edits the certificate in the database that was given as parameters
    public void updateCertificate(Certificate certificate) {
        certificateRepository.update(certificate);
    }
    
    // Deletes the certificate in the database that was given as parameters
    public void deleteCertificate(Certificate certificate) {
        certificateRepository.delete(certificate);
    }

    // Retrieves all certificates from the database
    public ArrayList<Certificate> retrieveAllCertificates() {
        return certificateRepository.retrieve();
    }
}
