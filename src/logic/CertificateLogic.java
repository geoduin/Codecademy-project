package logic;

import java.util.ArrayList;
import database.CertificateRepository;
import domain.Enrollment;
import domain.Certificate;

public class CertificateLogic {
    private CertificateRepository certificateRepository;

    public CertificateLogic() {
        this.certificateRepository = new CertificateRepository();
    }

    public void newCertificate(Enrollment enrollment, String employeeName, int grade) {
        int enrollmentID = enrollment.getId();
        Certificate certificate = new Certificate(enrollmentID, employeeName, grade);
        certificateRepository.insert(certificate);
    }

    public void updateCertificate(Certificate certificate) {
        certificateRepository.update(certificate);
    }

    public void deleteCertificate(Certificate certificate) {
        certificateRepository.delete(certificate);
    }

    public ArrayList<Certificate> retrieveAllCertificates() {
        return certificateRepository.retrieve();
    }
}
