package gui;

import java.util.List;
import java.util.Map;

import domain.Webcast;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
        final String defaultWebcastValue = "Select an organization";
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
            }else { 
                
            }
        });
        
        activate(view, "Student management view");
        
        
    }

    public void editWebcastView(Webcast webcastToEdit) { 
        
    }

    
}
