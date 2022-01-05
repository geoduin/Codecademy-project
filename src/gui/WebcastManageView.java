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
import logic.WebcastLogic;

class WebcastManageView extends View {
    private WebcastLogic logic;

    WebcastManageView(GUI baseUI) {
        super(baseUI);
        this.logic = new WebcastLogic();
    }

    @Override
    protected void createView() {

        GridPane view = generateGrid();

        // First column
        final String defaultWebcastValue = "Select a webcast";
        Label selectWebcast = new Label(defaultWebcastValue);
        ComboBox<String> webcastComboBox = new ComboBox<>();
        webcastComboBox.setValue(defaultWebcastValue);
        Label result = new Label();

        // retrieves all webcast names and adds them to the combox
        List<String> webcastList = this.logic.retrieveWebcastNames();
        for (int i = 0; i < webcastList.size(); i++) {
            webcastComboBox.getItems().add(webcastList.get(i));
        }

        // Will take you to edit view with values already
        Button editView = new Button("Edit");
        // Will delete selected webcast
        Button deleteView = new Button("Delete");

        // second column
        // Will take you to add view
        Label addWebcast = new Label("Add webcast");
        Button addView = new Button("+");

        //Third column
        Label titleLabel = new Label("Title");
        TextField titleTextField = new TextField();
        titleTextField.setEditable(false);

        Label descriptionLabel = new Label("Description");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setPrefHeight(50);

        Label durationInMinutesLabel = new Label("Duration (in minutes)");
        TextField durationTextField = new TextField();
        durationTextField.setEditable(false);

        Label speakerLabel = new Label("Speaker");
        TextField speakerTextField = new TextField();
        speakerTextField.setEditable(false);

        Label urlLabel = new Label("URL");
        TextField urlTextField = new TextField();
        urlTextField.setEditable(false);

        Label statusLabel = new Label("Status");
        TextField statusField = new TextField();
        statusField.setEditable(false);


       

        // Layout management view
        view.add(selectWebcast, 0, 0);
        view.add(webcastComboBox, 0, 1);
        view.add(editView, 0, 2);
        view.add(deleteView, 0, 3);
        view.add(addWebcast, 1, 0);
        view.add(addView, 1, 1);
        view.add(result, 0, 5);
        view.add(titleLabel, 2, 0);
        view.add(titleTextField, 2, 1);
        view.add(descriptionLabel, 2, 2);
        view.add(descriptionArea, 2, 3);
        view.add(durationInMinutesLabel, 2, 4);
        view.add(durationTextField, 2, 5);
        view.add(speakerLabel, 2, 6);
        view.add(speakerTextField, 2, 7);
        view.add(urlLabel, 2, 8);
        view.add(urlTextField, 2, 9);
        view.add(statusLabel, 2, 10);
        view.add(statusField, 2, 11);

        

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
            if (webcastComboBox.getValue().equals(defaultWebcastValue)) {
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
        
        //retrieves the selected webcast and displays it on the GUI.
        webcastComboBox.setOnAction(chosenWebcast -> {
            Webcast webcast = this.logic.retrieveByTitle(webcastComboBox.getValue());
            titleTextField.setText(webcast.getTitle());
            descriptionArea.setText(webcast.getDescription());
            durationTextField.setText(String.valueOf(webcast.getDurationInMinutes()));
            speakerTextField.setText(webcast.getSpeaker());
            urlTextField.setText(webcast.getUrl());
            statusField.setText(webcast.getStatus().toString());


        });

        // opens add webcast view
        addView.setOnAction(clicked -> {
            addWebcastView();
        });

        activate(view, "Webcast management view");

    }

    // String url, String title, String description, Status status
    protected void editWebcastView(Webcast webcastToEdit) {

        // Saving original URL since URL is the being used to identify the webcast in
        // the database, it has to be saved temporarily if you want to edit the URL.
        String originalURL = webcastToEdit.getUrl();
        // Column 1
        GridPane view = generateFormGrid();
        Label titleLabel = new Label("Title");
        TextField titleField = new TextField(webcastToEdit.getTitle());
        Label descriptionLabel = new Label("Description");
        TextArea descriptionArea = new TextArea(webcastToEdit.getDescription());
        Label urlLabel = new Label("URL");
        TextField urlField = new TextField(webcastToEdit.getUrl());
        Label statusLabel = new Label("Status");
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setValue(webcastToEdit.getStatus().toString());
        statusComboBox.getItems().add("ACTIVE");
        statusComboBox.getItems().add("CONCEPT");
        statusComboBox.getItems().add("ARCHIVED");
        Label result = new Label();

        // column 2
        Button editButton = new Button("Save edit");

        // layout
        view.add(titleLabel, 0, 0);
        view.add(titleField, 0, 1);
        view.add(descriptionLabel, 0, 2);
        view.add(descriptionArea, 0, 3);
        view.add(urlLabel, 0, 4);
        view.add(urlField, 0, 5);
        view.add(statusLabel, 0, 6);
        view.add(statusComboBox, 0, 7);
        view.add(editButton, 1, 7);
        view.add(result, 0, 8);

        editButton.setOnAction(click -> {
            // checking if all fields are filled
            Boolean noFieldEmpty = true;
            if (titleField.getText().isBlank() || descriptionArea.getText().isBlank() || urlField.getText().isBlank()) {
                noFieldEmpty = false;
            }
            if (noFieldEmpty) {
                // edditing the webcast
                this.logic.editURL(originalURL, urlField.getText());
                this.logic.editWebcast(urlField.getText(), titleField.getText(), descriptionArea.getText(),
                        Status.valueOf(statusComboBox.getValue()));
                        if(this.logic.updateSuccessful(titleField.getText(), descriptionArea.getText(), urlField.getText(), statusComboBox.getValue())) { 
                            result.setText("Update successful");
                        } else {
                            result.setText("Update failed");
                        }
                return;
            } else {
                result.setText("All fields must be filled");
                return;
            }
        });

        activate(view, "Edit webcast");

    }

    //Adds webcast to database
    protected void addWebcastView() {
        GridPane view = generateFormGrid();
        final String defaultStatusValue = "Select value";

        // column 1
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
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setValue(defaultStatusValue);
        statusComboBox.getItems().add("ACTIVE");
        statusComboBox.getItems().add("CONCEPT");
        statusComboBox.getItems().add("ARCHIVED");

        Label result = new Label();

        // column 2
        Button saveButton = new Button("Save");

        // Layout
        view.add(titleLabel, 0, 0);
        view.add(titleTextField, 0, 1);
        view.add(descriptionLabel, 0, 2);
        view.add(descriptionArea, 0, 3);
        view.add(durationInMinutesLabel, 0, 4);
        view.add(durationTextField, 0, 5);
        view.add(speakerLabel, 0, 6);
        view.add(speakerTextField, 0, 7);
        view.add(organizationLabel, 0, 8);
        view.add(organizationField, 0, 9);
        view.add(urlLabel, 0, 10);
        view.add(urlTextField, 0, 11);
        view.add(statusLabel, 0, 12);
        view.add(statusComboBox, 0, 13);
        view.add(saveButton, 1, 13);
        view.add(result, 0, 14);

        activate(view, "Add Webcast");

        // Event handler

        saveButton.setOnAction(clicked -> {
            //Checks if all fields are filled
            if (titleTextField.getText().isBlank() || descriptionArea.getText().isBlank()
                    || durationTextField.getText().isBlank() || speakerTextField.getText().isBlank()
                    || organizationField.getText().isBlank() || urlTextField.getText().isBlank()
                    || statusComboBox.getValue().equals(defaultStatusValue)) {
                        result.setText("All fields must be filled");
                return;
                //checks if the webcast already exists
            }else if(this.logic.titleAlreadyExists(titleTextField.getText())) { 
                result.setText("This webcast title already exists: " + titleTextField.getText());
                return;
            } else {

                //adds webcast to database
                int duration = Integer.valueOf(durationTextField.getText());
                this.logic.createWebcast(titleTextField.getText(), speakerTextField.getText(),
                        organizationField.getText(), duration, urlTextField.getText(), statusComboBox.getValue(),
                        descriptionArea.getText());
                        //tells user wether the webcast was successfully saved
                        if(this.logic.saveSuccessful(titleTextField.getText())) { 
                            result.setText("Save successful");
                        }else {
                            result.setText("Save failed");
                        }
                return;
            }

        });

    }

}
