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
        Button addView = new Button("+");


        //Layout management view
        view.add(selectStudent, 0, 0);
        view.add(webcastComboBox, 0, 1);
        view.add(editView, 0, 2);
        view.add(deleteView, 0, 3);
        view.add(addView, 1, 1);


        //event handlers
        editView.setOnAction(clicked -> {
            if(webcastComboBox.getValue().equals(defaultWebcastValue)) {
                view.add(new Label("Not selected anything"), 0, 5);
                return;
            }else { 
                String title = webcastComboBox.getValue();
                editWebcastView(logic.retrieveByTitle(title));
            }
        });
        
        activate(view, "Student management view");
        
        
    }
    //String url, String title, String description, Status status
    public void editWebcastView(Webcast webcastToEdit) { 
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
        

        activate(view, "Add webcast");



    }

    
}
