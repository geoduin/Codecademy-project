package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CertificateSubmissionView extends View {

    public CertificateSubmissionView(GUI baseUI) {
        super(baseUI);
    }

    @Override
    public void createView() {
        // Layout positioning elements
        GridPane view = generateGrid();
        VBox vbox = new VBox(4);
        vbox.setSpacing(2.0); 

        // UI components
        Label nameLabel = new Label("Name");
        Label gradeLabel = new Label("Grade");
        TextField nameTextField = new TextField();
        TextField gradeTextField = new TextField();
        Button submitButton = new Button("Submit");

        // Further layout setup
        vbox.getChildren().addAll(nameLabel, nameTextField, gradeLabel, gradeTextField, submitButton);
        view.add(vbox, 0, 0);

        activate(view, "Add Certificate");
    }

    public void certificateSuccessfullyAdded() {
        GridPane view = generateFormGrid();

        Label label = new Label("Successfully added certificate!");
        Button homeBtn = new Button("Home");
        Button addAnotherCertificateBtn = new Button("Add");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(addAnotherCertificateBtn, 2, 1);

        //Behavior of UI components
        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());

        activate(view, "Successfully added!");
    }
}
