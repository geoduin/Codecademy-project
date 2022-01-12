package gui;

import java.util.List;
import domain.Gender;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import logic.CourseLogic;
import logic.EnrollmentLogic;
import logic.StatisticsLogic;
import logic.StudentLogic;

public class StatisticsViews extends View {

    private StatisticsLogic logic;
    private CourseLogic courseLogic;
    private EnrollmentLogic enrollmentLogic;
    private StudentLogic studentLogic;

    public StatisticsViews(GUI baseUI) {
        super(baseUI);
        this.logic = new StatisticsLogic();
        this.courseLogic = new CourseLogic();
        this.enrollmentLogic = new EnrollmentLogic();
        this.studentLogic = new StudentLogic();

    }

    @Override
    protected void createView() {

        GridPane view = generateGrid();
        // First column of first row
        // Gender statistics
        Label genderStatistics = new Label("Gender Statistics");
        ComboBox<Gender> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().add(Gender.F);
        genderComboBox.getItems().add(Gender.M);
        genderComboBox.getItems().add(Gender.O);
        Text genderStatisticsValue = new Text();

        // Second column of first row
        Label averageProgressLabel = new Label("Average Progress");
        ComboBox<String> courseComboBox = new ComboBox<>();

        // Text item that'll contain the averages per module.
        Text averageProgressText = new Text();

        // Third column of first row
        Label progressStudentInCourseLabel = new Label("Progress per module for student in course");
        ComboBox<String> courseComboBoxForStudentCourseProgress = new ComboBox<>();

        ComboBox<String> studentComboBox = new ComboBox<>();
        final String defaultStudentComboBoxValue = "Select a student";
        studentComboBox.setValue(defaultStudentComboBoxValue);
        Text studentProgressText = new Text();

        // Fourth column of first row
        Label studentCertificatesLabel = new Label("Student certificates");
        ComboBox<String> allStudentsBox = new ComboBox<>();
        List<String> allStudents = this.studentLogic.retrieveAllEmails();
        // adding students to ComboBox.
        for (int i = 0; i < allStudents.size(); i++) {
            allStudentsBox.getItems().add(allStudents.get(i));
        }
        Text allCertificatesText = new Text();

        // First column of second row

        Label topWebcastLabel = new Label("Top 3 webcasts by views");
        Text topWebcastText = new Text(this.logic.top3WebcastFormatted());

        // Second column of second row
        Label topCourseLabel = new Label("Top 3 courses by number of certificates");
        Text topCoursesText = new Text(this.logic.top3CoursesFormatted());

        // Third column of second row

        Label recommendedCoursesLabel = new Label("Reccomended Course");
        ComboBox<String> courseBoxForRecommendedCourses = new ComboBox<>();
        Text recommendedCoursesText = new Text();

        // Fourth column of second row

        Label numberOfReceivedCertificatesLabel = new Label("Number of received certificates");
        ComboBox<String> courseBoxForNumberOfCertificates = new ComboBox<>();
        Text numberOfCertificatesText = new Text();

        // Layout of row 1
        view.add(genderStatistics, 0, 0);
        view.add(genderComboBox, 0, 1);
        view.add(genderStatisticsValue, 0, 2);
        view.add(averageProgressLabel, 1, 0);
        view.add(courseComboBox, 1, 1);
        view.add(averageProgressText, 1, 2);
        view.add(progressStudentInCourseLabel, 2, 0);
        view.add(courseComboBoxForStudentCourseProgress, 2, 1);
        // Added studentComboBox at 2,2 when a course is selected.
        view.add(studentProgressText, 2, 3);
        view.add(studentCertificatesLabel, 3, 0);
        view.add(allStudentsBox, 3, 1);
        view.add(allCertificatesText, 3, 2);

        // Layout of row 2
        view.add(topWebcastLabel, 0, 4);
        view.add(topWebcastText, 0, 5);
        view.add(topCourseLabel, 1, 4);
        view.add(topCoursesText, 1, 5);
        view.add(recommendedCoursesLabel, 2, 4);
        view.add(courseBoxForRecommendedCourses, 2, 5);
        view.add(recommendedCoursesText, 2, 6);
        view.add(numberOfReceivedCertificatesLabel, 3, 4);
        view.add(courseBoxForNumberOfCertificates, 3, 5);
        view.add(numberOfCertificatesText, 3, 6);

        // Event handlers
        genderComboBox.setOnAction(pickedGender -> {
            genderStatisticsValue.setText(this.logic.genderStatisticsFormatter(genderComboBox.getValue()));
        });

        courseComboBox.setOnAction(pickedCourse -> {
            averageProgressText.setText(this.logic.courseProgressStatisticFormatter(courseComboBox.getValue()));
        });

        courseComboBoxForStudentCourseProgress.setOnAction(pickedCourse -> {
            List<String> studentEmails = this.enrollmentLogic
                    .retrieveEmailsFromCourse(courseComboBoxForStudentCourseProgress.getValue());
            for (int i = 0; i < studentEmails.size(); i++) {
                studentComboBox.getItems().add(studentEmails.get(i));

            }
            view.add(studentComboBox, 2, 2);
        });

        studentComboBox.setOnAction(pickedStudent -> {
            studentProgressText.setText(this.logic.progressOfStudentFormatter(studentComboBox.getValue(),
                    courseComboBoxForStudentCourseProgress.getValue()));
        });

        allStudentsBox.setOnAction(pickedStudent -> {
            allCertificatesText.setText(
                    this.logic.certificateFormatter(this.logic.retrieveCertificates(allStudentsBox.getValue())));
        });

        courseBoxForRecommendedCourses.setOnAction(pickedCourse -> {
            recommendedCoursesText
                    .setText(this.logic.recommendedCourseFormatter(courseBoxForRecommendedCourses.getValue()));
        });

        courseBoxForNumberOfCertificates.setOnAction(pickedCourse -> {
            numberOfCertificatesText
                    .setText(this.logic.numberOfCertificatesFormatter(courseBoxForNumberOfCertificates.getValue()));
        });

        // adding values to all courseComboboxes
        List<String> courses = this.courseLogic.retrieveCourseNames();
        // Adding courses to ComboBox.
        for (int i = 0; i < courses.size(); i++) {
            courseComboBox.getItems().add(courses.get(i));
            courseComboBoxForStudentCourseProgress.getItems().add(courses.get(i));
            courseBoxForRecommendedCourses.getItems().add(courses.get(i));
            courseBoxForNumberOfCertificates.getItems().add(courses.get(i));

        }

        activate(view, "test");

    }

    // Possible features to add:

    // SearchBar for student
    // HBox studentSearch = new HBox();
    // TextField searchBar = new TextField("Student Email");
    // Button searchButton = new Button("Search");

}
