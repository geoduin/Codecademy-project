package gui;

import java.util.List;

import domain.Status;
import domain.Webcast;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import logic.InputValidation;
import logic.WebcastLogic;

//Class responsible for all the views that are related to the management of webcasts (add, edit, delete modules)
class WebcastManageView extends View {
    private WebcastLogic logic;

    WebcastManageView(GUI baseUI) {
        super(baseUI);
        this.logic = new WebcastLogic();

    }

    // Method is fully explained in the abstract class this subclass implements.
    @Override
    protected void createView() {

        GridPane view = generateGrid();

        // First column
        final String defaultWebcastValue = "Select a webcast";
        Label selectWebcast = new Label(defaultWebcastValue);
        ComboBox<String> webcastComboBox = new ComboBox<>();
        webcastComboBox.setValue(defaultWebcastValue);
        Label result = new Label();

        // retrieves all webcast names and adds them to the ComboBox
        List<String> webcastList = this.logic.retrieveWebcastNames();
        for (int i = 0; i < webcastList.size(); i++) {
            webcastComboBox.getItems().add(webcastList.get(i));
        }

        // Will take you to edit view with values already
        Button editView = new Button("Edit");
        editView.setId("editButton");
        // Will delete selected webcast
        Button deleteView = new Button("Delete");
        deleteView.setId("deleteButton");

        // second column
        // Will take you to add view
        Label addWebcast = new Label("Add webcast");
        Button addView = new Button("+");
        addView.setId("addButton");

        // Layout management view
        view.add(selectWebcast, 0, 0);
        view.add(webcastComboBox, 0, 1);
        view.add(editView, 0, 2);
        view.add(deleteView, 0, 3);
        view.add(addWebcast, 1, 0);
        view.add(addView, 1, 1);
        view.add(result, 0, 5);

        // event handlers

        // changes to edit view
        editView.setOnAction(clicked -> {
            if (webcastComboBox.getValue().equals(defaultWebcastValue)) {
                result.setText("Please select a webcast");
                return;
            } else {
                String title = webcastComboBox.getValue();
                editWebcastView(logic.retrieveByTitle(title));
            }
        });
        // Deletes webcast
        deleteView.setOnAction(clicked -> {
            if (webcastComboBox.getValue().equals(defaultWebcastValue) || webcastComboBox.getValue() == null) {
                result.setText("Please select a webcast");
                return;
            } else {
                this.logic.deleteWebcast(this.logic.retrieveByTitle(webcastComboBox.getValue()));
                result.setText("Deletion successful");
                webcastComboBox.getItems().remove(webcastComboBox.getValue());
                webcastComboBox.setValue(defaultWebcastValue);

                return;
            }

        });

        // opens add webcast view
        addView.setOnAction(clicked -> {
            addWebcastView();
        });

        this.gui.goToNext(view, "Webcast management view");

    }

    // String url, String title, String description, Status status
    private void editWebcastView(Webcast webcastToEdit) {

        // Saving original URL since URL is the being used to identify the webcast in
        // the database, it has to be saved temporarily if you want to edit the URL.
        String originalURL = webcastToEdit.getUrl();
        String originalTitle = webcastToEdit.getTitle();

        // Column 1
        GridPane view = generateFormGrid();
        Label titleLabel = new Label("Title");
        TextField titleField = new TextField(webcastToEdit.getTitle());
        Label descriptionLabel = new Label("Description");
        TextArea descriptionArea = new TextArea(webcastToEdit.getDescription());
        Label urlLabel = new Label("URL");
        TextField urlField = new TextField(webcastToEdit.getUrl());
        Label statusLabel = new Label("Status");
        ComboBox<Status> statusComboBox = new ComboBox<>();
        statusComboBox.setValue(webcastToEdit.getStatus());
        statusComboBox.getItems().addAll(Status.ACTIVE, Status.ARCHIVED, Status.CONCEPT);

        Label result = new Label("");

        // Column 2
        Button editButton = new Button("Save edit");

        // Layout
        view.add(titleLabel, 0, 0);
        view.add(titleField, 1, 0);
        view.add(descriptionLabel, 0, 1);
        view.add(descriptionArea, 1, 1);
        view.add(urlLabel, 0, 2);
        view.add(urlField, 1, 2);
        view.add(statusLabel, 0, 3);
        view.add(statusComboBox, 1, 3);
        view.add(editButton, 1, 5);
        view.add(result, 0, 6);

        editButton.setOnAction(click -> {
            // Checking if all fields are filled
            Boolean noFieldEmpty = true;
            if (titleField.getText().isBlank() || descriptionArea.getText().isBlank() || urlField.getText().isBlank()) {
                noFieldEmpty = false;
                result.setText("All fields must be filled in");
            } else if (!InputValidation.isValidURL(urlField.getText())) {
                result.setText("url format is incorrect");
            }

            if (noFieldEmpty) {
                // Editing the webcast, editWebcast returns, the editWebcast method returns a
                // string saying wether the update was successful and giving details about the
                // fail if it wasn't.
                result.setText(this.logic.editWebcast(originalURL, urlField.getText(), originalTitle,
                        titleField.getText(), descriptionArea.getText(), statusComboBox.getValue()));

            }
        });

        this.gui.goToNext(view, "Edit webcast");

    }

    // View to add webcasts to the database
    private void addWebcastView() {
        GridPane view = generateFormGrid();


        // Column 1
        Label titleLabel = new Label("Title");
        TextField titleTextField = new TextField();

        Label descriptionLabel = new Label("Description");
        TextArea descriptionArea = new TextArea();

        Label durationInMinutesLabel = new Label("Duration (in minutes)");
        TextField durationTextField = new TextField();

        Label speakerLabel = new Label("Speaker");
        TextField speakerTextField = new TextField();

        Label organizationLabel = new Label("Organization");
        TextField organizationField = new TextField();

        Label urlLabel = new Label("URL");
        TextField urlTextField = new TextField();

        Label statusLabel = new Label("Status");
        ComboBox<Status> statusComboBox = new ComboBox<>();
        statusComboBox.setId("selectBox");

        statusComboBox.getItems().addAll(Status.ACTIVE, Status.ARCHIVED, Status.CONCEPT);
        Label viewCount = new Label("Views");
        TextField viewsField = new TextField();
        Label result = new Label();

        // Column 2
        Button saveButton = new Button("Save");

        // Layout
        view.add(titleLabel, 0, 1);
        view.add(titleTextField, 1, 1);
        view.add(descriptionLabel, 0, 2);
        view.add(descriptionArea, 1, 2);
        view.add(durationInMinutesLabel, 0, 3);
        view.add(durationTextField, 1, 3);
        view.add(speakerLabel, 0, 4);
        view.add(speakerTextField, 1, 4);
        view.add(organizationLabel, 0, 5);
        view.add(organizationField, 1, 5);
        view.add(urlLabel, 0, 6);
        view.add(urlTextField, 1, 6);
        view.add(statusLabel, 0, 7);
        view.add(statusComboBox, 1, 7);
        view.add(viewCount, 0, 8);
        view.add(viewsField, 1, 8);
        view.add(saveButton, 1, 9);
        view.add(result, 2, 9);

        this.gui.goToNext(view, "Add Webcast");

        // Event handler

        saveButton.setOnAction(clicked -> {
            // Checks if all fields are filled
            if (titleTextField.getText().isBlank() || descriptionArea.getText().isBlank()
                    || durationTextField.getText().isBlank() || speakerTextField.getText().isBlank()
                    || organizationField.getText().isBlank() || urlTextField.getText().isBlank())
                     {
                result.setText("All fields must be filled");
                return;

            } else if (!InputValidation.isValidURL(urlTextField.getText())) {
                result.setText("Incorrect url format");
                return;
            } else {
                result.setText(this.logic.createWebcast(titleTextField.getText(), speakerTextField.getText(),
                        organizationField.getText(), Integer.valueOf(durationTextField.getText()),
                        urlTextField.getText(), statusComboBox.getValue(), descriptionArea.getText(),
                        Integer.valueOf(viewsField.getText())));
            }

        });

    }

}