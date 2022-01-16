package gui;

import domain.Certificate;
import domain.Enrollment;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import logic.EnrollmentLogic;
import logic.InputValidation;
import logic.CertificateLogic;

public class CertificateManagementView extends View {
    private CertificateLogic certificateLogic;

    public CertificateManagementView(GUI baseUI) {
        super(baseUI);
        this.certificateLogic = new CertificateLogic();
    }

    @Override
    public void createView() {
        // Layout positioning elements
        GridPane view = generateGrid();

        //  UI Components
        Label enrollmentsLabel = new Label("Eligible enrollments");
        ComboBox<Enrollment> enrollmentDropdown = new ComboBox<>();
        Button createCertificateButton = new Button("Create");
        Label certificatesLabel = new Label("Manage certificates");
        ComboBox<Certificate> certificateDropdown = new ComboBox<>();
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        //  Further layout setup
        view.add(enrollmentsLabel, 0, 0);
        view.add(enrollmentDropdown, 0, 1);
        view.add(createCertificateButton, 0, 2);
        view.add(certificatesLabel, 0, 3);
        view.add(certificateDropdown, 0, 4);
        view.add(editButton, 0, 5);
        view.add(deleteButton, 0, 6);

        //  Setup of the enrollmentDropdown containing eligible enrollments setup
        // Adding enrollment instances to the dropdown
        int count = 0;
        for (Enrollment enrollment : new EnrollmentLogic().getEnrollmentsEligibleForCertificate()) {
            enrollmentDropdown.getItems().add(enrollment);
            count++;
        }
        enrollmentDropdown.setPromptText(count + " enrollment(s) available");

        // Giving the user an option to create a certificate with the selected
        // enrollment from the EnrollmentsDropdown
        createCertificateButton.setOnMousePressed(clicked -> {
            if (enrollmentDropdown.getValue() == null) {
                return;
            }
            addCertificateView(enrollmentDropdown.getValue());
        });

        // Allows user to edit certificate that is selected in the CertificateDropdown
        editButton.setOnMouseClicked(clicked -> {
            if (certificateDropdown.getValue() == null) {
                return;
            }
            editCertificateView(certificateDropdown.getValue());
        });

        // Allows user to delete certificate that is selected in the CertificateDropdown
        deleteButton.setOnMouseClicked(clicked -> {
            if (certificateDropdown.getValue() == null) {
                return;
            }
            
            certificateLogic.deleteCertificate(certificateDropdown.getValue());
            certificateSuccessfullyDeleted();
        });

        //  Adding certificates to certificateDropdown
        certificateDropdown.getItems().addAll(certificateLogic.retrieveAllCertificates());
        certificateDropdown.setPromptText(certificateLogic.retrieveAllCertificates().size() + " certificate(s) available");

        this.gui.goToNext(view, "Manage certificates");
    }

    public void certificateSuccessfullyDeleted() {
        // Layout positioning elements
        GridPane view = generateGrid();

        // UI components
        Label label = new Label("Successfully deleted certificate!");
        Button homeBtn = new Button("Home");
        Button addAnotherCertificateBtn = new Button("Delete");
        
        // Further layout setup
        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(addAnotherCertificateBtn, 2, 1);

        // Behavior of UI components
        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());

        this.gui.goToNext(view, "Successfully deleted!");
    }

    public void addCertificateView(Enrollment enrollment) {
        // Layout positioning elements
        GridPane view = generateGrid();

        
        Label nameLabel = new Label("Name");
        Label gradeLabel = new Label("Grade");
        TextField nameTextField = new TextField();
        Label incorrectNameLabel = new Label();
        TextField gradeTextField = new TextField();
        Label incorrectGradeLabel = new Label();
        Button submitButton = new Button("Submit");

        // Further layout setup
        view.add(nameLabel, 0, 0);
        view.add(nameTextField, 0, 1);
        view.add(incorrectNameLabel, 1, 1);
        view.add(gradeLabel, 0 , 2);
        view.add(gradeTextField, 0 , 3);
        view.add(incorrectGradeLabel, 1, 3);
        view.add(submitButton, 0, 4);

        // Checking if input is correct
        submitButton.setOnMouseClicked(clicked -> {
            String employeeName = nameTextField.getText();
            String grade = gradeTextField.getText();

            if (nameTextField.getText().isBlank()) {
                incorrectNameLabel.setText("Please enter a valid name");
                return;
            }
            incorrectNameLabel.setText("");

            if (!InputValidation.areNumbers(grade)) {
                incorrectGradeLabel.setText("Please enter a valid number");
                return;
            }
            incorrectGradeLabel.setText("");

            int gradeInt = Integer.valueOf(grade);

            if (!InputValidation.isValidGrade(gradeInt) || gradeTextField.getText().isBlank()) {
                incorrectGradeLabel.setText("Please enter a number between 1-10");
                return;
            }

            certificateLogic.newCertificate(enrollment, employeeName, gradeInt);
            certificateSuccessfullyAdded();
        });

        this.gui.goToNext(view, "Create Certificate for " + enrollment.getStudentEmail());
    }

    public void certificateSuccessfullyAdded() {
        GridPane view = generateGrid();

        Label label = new Label("Successfully added certificate!");
        Button homeBtn = new Button("Home");
        Button addAnotherCertificateBtn = new Button("Add");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(addAnotherCertificateBtn, 2, 1);

        // Behavior of UI components
        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());

        this.gui.goToNext(view, "Successfully added!");
    }

    public void editCertificateView(Certificate certificate) {
        // Layout positioning elements
        GridPane view = generateGrid();

        // UI components
        Label editCertificateLabel = new Label("Edit certificate");
        Label nameLabel = new Label("Name: ");
        TextField nameTextField = new TextField(certificate.getNameOfIssuer());
        Label incorrectNameLabel = new Label();
        Label gradeLabel = new Label("Grade: ");
        TextField gradeTextField = new TextField(String.valueOf(certificate.getGrade()));
        Label incorrectGradeLabel = new Label();
        Button editButton = new Button("Edit");

        // Further layout setup
        view.add(editCertificateLabel, 0, 0);
        view.add(nameLabel, 0, 1);
        view.add(nameTextField, 1, 1);
        view.add(incorrectNameLabel, 2, 1);
        view.add(gradeLabel, 0, 2);
        view.add(gradeTextField, 1 ,2);
        view.add(incorrectGradeLabel, 2, 2);
        view.add(editButton, 0, 3);

        editButton.setOnMouseClicked(clicked -> {
            String employeeName = nameTextField.getText();
            String grade = gradeTextField.getText();

            if (nameTextField.getText().isBlank()) {
                incorrectNameLabel.setText("Please enter a valid name");
                return;
            }
            incorrectNameLabel.setText("");

            if (!InputValidation.areNumbers(grade)) {
                incorrectGradeLabel.setText("Please enter a valid number");
                return;
            }
            incorrectGradeLabel.setText("");

            int gradeInt = Integer.valueOf(grade);

            if (!InputValidation.isValidGrade(gradeInt) || gradeTextField.getText().isBlank()) {
                incorrectGradeLabel.setText("Please enter a number between 1-10");
                return;
            }
            incorrectGradeLabel.setText("");

            certificate.setNameOfIssuer(employeeName);
            certificate.setGrade(gradeInt);
            certificateLogic.updateCertificate(certificate);
            certificateSuccessfullyUpdated();
        });
        
        this.gui.goToNext(view, "Edit certificate");
    }

    public void certificateSuccessfullyUpdated() {
        // Layout positioning elements
        GridPane view = generateGrid();

        // UI components
        Label label = new Label("Successfully updated certificate!");
        Button homeBtn = new Button("Home");
        Button addAnotherCertificateBtn = new Button("Update");

        // Further layout setup
        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(addAnotherCertificateBtn, 2, 1);

        // Behavior of UI components
        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());

        this.gui.goToNext(view, "Successfully updated!");
    }
}
