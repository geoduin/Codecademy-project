package gui;

import domain.Gender;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import logic.StatisticsLogic;

public class StatisticsViews extends View{

    private StatisticsLogic logic;

    public StatisticsViews(GUI baseUI) { 
        super(baseUI);
        this.logic = new StatisticsLogic();
    }


    @Override
    protected void createView() {

        GridPane view = generateGrid();
        //First Column
        //Gender statistics
        Label genderStatistics = new Label("Gender Statistics");
        ComboBox<Gender> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().add(Gender.F);
        genderComboBox.getItems().add(Gender.M);
        genderComboBox.getItems().add(Gender.O);
        Text genderStatisticsValue = new Text();

        


        //View layout
        view.add(genderStatistics, 0, 0);
        view.add(genderComboBox, 0, 1);
        view.add(genderStatisticsValue, 0, 2);



        //Event handlers
        genderComboBox.setOnAction(pickedGender -> {
            genderStatisticsValue.setText(this.logic.genderStatisticsFormatter(genderComboBox.getValue()));
        });


        activate(view, "test");
        
        
        
    }
    
}
