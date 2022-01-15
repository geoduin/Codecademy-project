package gui;

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
        // Initializing view (layout)
        GridPane view = generateGrid();

        // Main UI components
        Label mainLabel = new Label("Enrollments that are eligible for a certificate:");
        ComboBox<Enrollment> enrollmentDropdown = new ComboBox<>();
        Button createCertificateBtn = new Button("Create certificate for selected enrollment");

        // Actual setup of the dropdown containing eligible enrollments setup
        // Adding enrollment instances to the dropdown
        int count = 0;
        for (Enrollment enrollment : new EnrollmentLogic().getEnrollmentsEligibleForCertificate()) {
            enrollmentDropdown.getItems().add(enrollment);
            count++;
        }
        enrollmentDropdown.setPromptText(count + " enrollment(s) available");

        // Giving the user an option to create a certificate with the selected
        // enrollment from the dropdown
        createCertificateBtn.setOnMousePressed(clicked -> {
            if (enrollmentDropdown.getValue() == null) {
                return;
            }
            addCertificateView(enrollmentDropdown.getValue());
        });

        // Finalization and activation of view
        view.add(mainLabel, 0, 0);
        view.add(enrollmentDropdown, 0, 1);
        view.add(createCertificateBtn, 0, 2);

        activate(view, "Select enrollment eligible for certificate creation");
    }

    public void addCertificateView(Enrollment enrollment) {
        // Layout positioning elements
        GridPane view = generateGrid();
        

        // UI components
        Label nameLabel = new Label("Name");
        Label gradeLabel = new Label("Grade");
        TextField nameTextField = new TextField();
        TextField incorrectNameTextField = new TextField();
        TextField gradeTextField = new TextField();
        TextField incorrectGradeTextField = new TextField();
        Button submitButton = new Button("Submit");

        // Further layout setup
        view.add(nameLabel, 0, 0);
        view.add(nameTextField, 0, 1);
        view.add(incorrectNameTextField, 1, 1);
        view.add(gradeLabel, 0 , 2);
        view.add(gradeTextField, 0 , 3);
        view.add(incorrectGradeTextField, 1, 3);
        view.add(submitButton, 0, 4);

        // Checking input
        submitButton.setOnMouseClicked(clicked -> {
            String employeeName = nameTextField.getText();
            int grade = Integer.valueOf(gradeTextField.getText());

            if (!InputValidation.areLetters(employeeName) || nameTextField.getText().isBlank()) {
                incorrectNameTextField.setText("Please enter a valid name");
                return;
            } 

            if (!InputValidation.isValidGrade(grade) || gradeTextField.getText().isBlank()) {
                incorrectGradeTextField.setText("Please enter a number between 0-10");
                return;
            }

            certificateLogic.newCertificate(enrollment, employeeName, grade);
            certificateSuccessfullyAdded();
        });

        activate(view, "Create Certificate for " + enrollment.getStudentEmail());
    }

    public void certificateSuccessfullyAdded() {
        GridPane view = generateFormGrid();

        Label label = new Label("Successfully added certificate!");
        Button homeBtn = new Button("Home");
        Button addAnotherCertificateBtn = new Button("Add");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(addAnotherCertificateBtn, 2, 1);

        // Behavior of UI components
        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());

        activate(view, "Successfully added!");
    }
}
