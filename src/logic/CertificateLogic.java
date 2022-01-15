package logic;

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
}
