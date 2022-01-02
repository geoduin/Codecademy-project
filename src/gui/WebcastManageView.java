package gui;

import java.util.List;
import java.util.Map;

import domain.Status;
import domain.Webcast;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logic.WebcastLogic;

public class WebcastManageView extends View{
    private WebcastLogic logic;

    public WebcastManageView(GUI baseUI){
        super(baseUI);
        this.logic = new WebcastLogic();
    }

    @Override

    public void createView() {

        GridPane view = generateGrid();

        //First column
        final String defaultWebcastValue = "Select a webcast";
        Label selectStudent = new Label(defaultWebcastValue);
        ComboBox<String> webcastComboBox = new ComboBox<>();
        webcastComboBox.setValue(defaultWebcastValue);

        //retrieves all webcast names and adds them to the combox
        List<String> webcastList = this.logic.retrieveWebcastNames();
        for(int i = 0; i < webcastList.size(); i++) { 
            webcastComboBox.getItems().add(webcastList.get(i));
        }

        //Will take you to edit view with values already 
        Button editView = new Button("Edit");
        //Will delete selected webcast
        Button deleteView = new Button("Delete");

        //second column
        //Will take you to add view
        Label addWebcast = new Label("Add webcast");
        Button addView = new Button("+");


        //Layout management view
        view.add(selectStudent, 0, 0);
        view.add(webcastComboBox, 0, 1);
        view.add(editView, 0, 2);
        view.add(deleteView, 0, 3);
        view.add(addWebcast, 1, 0);
        view.add(addView, 1, 1);


        //event handlers

        //changes to edit view
        editView.setOnAction(clicked -> {
            if(webcastComboBox.getValue().equals(defaultWebcastValue)) {
                view.add(new Label("Please select a webcast"), 0, 5);
                return;
            }else { 
                String title = webcastComboBox.getValue();
                editWebcastView(logic.retrieveByTitle(title));
            }
        });
        //Deletes webcast
        deleteView.setOnAction(clicked -> {
            if(webcastComboBox.getValue().equals(defaultWebcastValue)) { 
                view.add(new Label("Please select a webcast"), 0, 5);
                return;
            }else { 
                this.logic.deleteWebcast(this.logic.retrieveByTitle(webcastComboBox.getValue()));
                view.add(new Label("Deletion successfull"), 0, 5);
                webcastComboBox.setValue(defaultWebcastValue);
                return;
            }


        });

        //opens add webcast view
        addView.setOnAction(clicked -> {
            addWebcastView();
        });
        
        activate(view, "Webcast management view");
        
        
    }
    //String url, String title, String description, Status status
    public void editWebcastView(Webcast webcastToEdit) { 

        //Saving original URL since URL is the being used to identify the webcast in the database, it has to be saved temporarily if you want to edit the URL.
        String originalURL = webcastToEdit.getUrl();
        //Column 1
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

        //column 2
        Button editButton = new Button("Save edit");



        //layout
        view.add(titleLabel, 0, 0);
        view.add(titleField, 0, 1);
        view.add(descriptionLabel, 0, 2);
        view.add(descriptionArea, 0, 3);
        view.add(urlLabel, 0, 4);
        view.add(urlField, 0, 5);
        view.add(statusLabel, 0, 6);
        view.add(statusComboBox, 0, 7);
        view.add(editButton, 1, 7);

        editButton.setOnAction(click -> {
            //checking if all fields are filled
            Boolean noFieldEmpty = true;
            if(titleField.getText().isBlank()|| descriptionArea.getText().isBlank() || urlField.getText().isBlank()) {
                noFieldEmpty = false;
            }
            if(noFieldEmpty) { 
                //edditing the webcast
                this.logic.editURL(originalURL, urlField.getText());
                this.logic.editWebcast(urlField.getText(), titleField.getText(), descriptionArea.getText(), Status.valueOf(statusComboBox.getValue()));
                Label successLabel = new Label("Success");
                view.add(successLabel, 0, 8);
                return;
            }else{
                Label fieldIsempty = new Label("No field may be empty");
                view.add(fieldIsempty, 0, 8);
                return;
            }
        });
        

        activate(view, "Edit webcast");




    }

    public void addWebcastView(){
        GridPane view = generateFormGrid();
        final String defaultStatusValue = "Select value";

        //column 1
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

        
        //column 2
        Button saveButton = new Button("Save");

        
        //Layout
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

        activate(view, "Add Webcast");

        //Event handler

        saveButton.setOnAction(clicked -> {
            boolean allFieldsFilled = true;
            if(titleTextField.getText().isBlank() || descriptionArea.getText().isBlank() || durationTextField.getText().isBlank() || speakerTextField.getText().isBlank() || organizationField.getText().isBlank() || urlTextField.getText().isBlank() || statusComboBox.getValue().equals(defaultStatusValue)) {
                allFieldsFilled = false;
                view.add(new Label("All fields must be filled"), 0, 14);
                return;
            } else { 
                int duration = Integer.valueOf(durationTextField.getText());
                this.logic.createWebcast(titleTextField.getText(), speakerTextField.getText(), organizationField.getText(), duration, urlTextField.getText(), statusComboBox.getValue(), descriptionArea.getText());
                view.add(new Label("Success"), 0, 14);
            }


        });

    








    }





    
}
