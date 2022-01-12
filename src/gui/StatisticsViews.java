package gui;


import java.util.List;
import domain.Gender;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import logic.CourseLogic;
import logic.EnrollLogic;
import logic.StatisticsLogic;
import logic.StudentLogic;

public class StatisticsViews extends View{

    private StatisticsLogic logic;
    private CourseLogic courseLogic;
    private EnrollLogic enrollLogic;
    private StudentLogic studentLogic;

    public StatisticsViews(GUI baseUI) { 
        super(baseUI);
        this.logic = new StatisticsLogic();
        this.courseLogic = new CourseLogic();
        this.enrollLogic = new EnrollLogic();
        this.studentLogic = new StudentLogic();

    }


    @Override
    protected void createView() {

        GridPane view = generateGrid();
        //First column
        //Gender statistics
        Label genderStatistics = new Label("Gender Statistics");
        ComboBox<Gender> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().add(Gender.F);
        genderComboBox.getItems().add(Gender.M);
        genderComboBox.getItems().add(Gender.O);
        Text genderStatisticsValue = new Text();

        //Second column
        Label averageProgressLabel = new Label("Average Progress");
        ComboBox<String> courseComboBox = new ComboBox<>();

        //Text item that'll contain the averages per module.
        Text averageProgressText = new Text();




        //Third column
        Label progressStudentInCourseLabel = new Label("Progress per module for student in course");
        ComboBox<String> courseComboBoxForStudentCourseProgress = new ComboBox<>();

        ComboBox<String> studentComboBox = new ComboBox<>();
        final String defaultStudentComboBoxValue = "Select a student";
        studentComboBox.setValue(defaultStudentComboBoxValue);
        Text studentProgressText = new Text();




        //adding values to both courseComboboxes
        List<String> courses = this.courseLogic.retrieveCourseNames();
        //Adding courses to ComboBox.
        for(int i = 0; i < courses.size(); i++) { 
            courseComboBox.getItems().add(courses.get(i));
            courseComboBoxForStudentCourseProgress.getItems().add(courses.get(i));

        }






        


        //View layout
        view.add(genderStatistics, 0, 0);
        view.add(genderComboBox, 0, 1);
        view.add(genderStatisticsValue, 0, 2);
        view.add(averageProgressLabel, 1, 0);
        view.add(courseComboBox, 1, 1);
        view.add(averageProgressText, 1, 2);
        view.add(progressStudentInCourseLabel, 2, 0);
        view.add(courseComboBoxForStudentCourseProgress, 2, 1);
        //Added studentCombobox at 2,2 when a course is selected.
        view.add(studentProgressText, 2, 3);
 



        //Event handlers
        genderComboBox.setOnAction(pickedGender -> {
            genderStatisticsValue.setText(this.logic.genderStatisticsFormatter(genderComboBox.getValue()));
        });

        courseComboBox.setOnAction(pickedCourse -> {
            averageProgressText.setText(this.logic.courseProgressStatisticFormatter(courseComboBox.getValue()));
        });

        courseComboBoxForStudentCourseProgress.setOnAction(pickedCourse -> {
           List<String> studentEmails = this.enrollLogic.retrieveEmailsFromCourse(courseComboBoxForStudentCourseProgress.getValue());
           for(int i = 0; i < studentEmails.size(); i++) { 
               studentComboBox.getItems().add(studentEmails.get(i));
               
           }
           view.add(studentComboBox, 2, 2);
        });

        studentComboBox.setOnAction(pickedStudent -> {
            studentProgressText.setText(this.logic.progressOfStudentFormatter(studentComboBox.getValue(), courseComboBoxForStudentCourseProgress.getValue()));
        });

       






        activate(view, "test");
        
        
        
    }


    //Possible features to add: 

    //SearchBar for student
    // HBox studentSearch = new HBox();
    // TextField searchBar = new TextField("Student Email");
    // Button searchButton = new Button("Search");
    
}
