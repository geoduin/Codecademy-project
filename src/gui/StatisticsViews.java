package gui;


import java.util.List;
import domain.Gender;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import logic.CourseLogic;
import logic.StatisticsLogic;

public class StatisticsViews extends View{

    private StatisticsLogic logic;
    private CourseLogic courseLogic;

    public StatisticsViews(GUI baseUI) { 
        super(baseUI);
        this.logic = new StatisticsLogic();
        this.courseLogic = new CourseLogic();
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

        //Second Column
        Label averageProgressLabel = new Label("Average Progress");
        ComboBox<String> courseComboBox = new ComboBox<>();
        //adding values to moduleComboBox.
        List<String> courses = this.courseLogic.retrieveCourseNames();
        for(int i = 0; i < courses.size(); i++) { 
            courseComboBox.getItems().add(courses.get(i));
        }
        //Text item that'll contain the averages per module.
        Text averageProgressText = new Text();

        


        //View layout
        view.add(genderStatistics, 0, 0);
        view.add(genderComboBox, 0, 1);
        view.add(genderStatisticsValue, 0, 2);
        view.add(averageProgressLabel, 1, 0);
        view.add(courseComboBox, 1, 1);
        view.add(averageProgressText, 1, 2);



        //Event handlers
        genderComboBox.setOnAction(pickedGender -> {
            genderStatisticsValue.setText(this.logic.genderStatisticsFormatter(genderComboBox.getValue()));
        });

        courseComboBox.setOnAction(pickedModule -> {
            averageProgressText.setText(this.logic.courseProgressStatisticFormatter(courseComboBox.getValue()));
        });




        activate(view, "test");
        
        
        
    }
    
}
