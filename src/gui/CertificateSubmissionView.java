package gui;

import domain.Enrollment;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import logic.EnrollmentLogic;

public class CertificateSubmissionView extends View {

    public CertificateSubmissionView(GUI baseUI) {
        super(baseUI);
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
        HBox hBox = new HBox(4);

        // UI components
        Label nameLabel = new Label("Name");
        Label gradeLabel = new Label("Grade");
        TextField nameTextField = new TextField();
        TextField gradeTextField = new TextField();
        Button submitButton = new Button("Submit");

        // Further layout setup
        hBox.getChildren().addAll(nameLabel, nameTextField, gradeLabel, gradeTextField, submitButton);
        view.add(hBox, 0, 0);

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
