package gui;

import java.util.List;
import domain.Gender;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
        BorderPane view = new BorderPane();
        GridPane grid = generateGrid();

        Label label = new Label("Statistics");
        label.setId("title");
        Button top3Webcast = new Button("Top 3 webcast");
        top3Webcast.setId("statBtn");
        Button AvgProgress = new Button("Average progress");
        AvgProgress.setId("statBtn");
        Button progressModule = new Button("Progress per module");
        progressModule.setId("statBtn");
        Button amountCert = new Button("Amount of certificates\nper student");
        amountCert.setId("statBtn");
        Button genderStat = new Button("Gender statistics");
        genderStat.setId("statBtn");
        Button top3CourseWithCert = new Button("Top 3 courses\nwith courses");
        top3CourseWithCert.setId("statBtn");
        Button recommendedCourse = new Button("Recommended\ncourses");
        recommendedCourse.setId("statBtn");
        Button receivedCerts = new Button("Received certificates\nper course");
        receivedCerts.setId("statBtn");

        genderStat.setOnMouseClicked(click -> genderStatistics());
        AvgProgress.setOnMouseClicked(click -> averageProgress());
        progressModule.setOnMouseClicked(click -> progressPerModule());
        amountCert.setOnMouseClicked(click -> Amountcertificates());

        top3Webcast.setOnMouseClicked(click -> top3WebcastView());
        top3CourseWithCert.setOnMouseClicked(click -> top3CoursesWithCert());
        recommendedCourse.setOnMouseClicked(click -> recommendedCourses());
        receivedCerts.setOnMouseClicked(click -> nrReceivedCertificates());

        view.setTop(label);
        grid.add(top3Webcast, 0, 1);
        grid.add(AvgProgress, 1, 1);
        grid.add(progressModule, 2, 1);
        grid.add(amountCert, 3, 1);
        grid.add(genderStat, 0, 2);
        grid.add(top3CourseWithCert, 1, 2);
        grid.add(recommendedCourse, 2, 2);
        grid.add(receivedCerts, 3, 2);
        view.setCenter(grid);
        view.setAlignment(label, Pos.CENTER);
        this.gui.goToNext(view, "Statistics");

    }

    // Possible features to add:

    // SearchBar for student
    // HBox studentSearch = new HBox();
    // TextField searchBar = new TextField("Student Email");
    // Button searchButton = new Button("Search");

    private void top3WebcastView() {
        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());

        // First column of second row
        GridPane view = generateFormGrid();
        Label topWebcastLabel = new Label("Top 3 webcasts by views");
        Text topWebcastText = new Text(this.logic.top3WebcastFormatted());

        view.add(returnBtn, 5, 0);
        view.add(topWebcastLabel, 1, 1);
        view.add(topWebcastText, 1, 2);
        this.gui.goToNext(view, "Top 3 most watched webcasts");
    }

    private void averageProgress() {
        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());

        GridPane view = generateGrid();
        // Second column of first row
        Label averageProgressLabel = new Label("Average Progress");
        ComboBox<String> courseComboBox = new ComboBox<>();
        // Text item that'll contain the averages per module.
        Text averageProgressText = new Text();

        courseComboBox.setOnAction(pickedCourse -> {
            averageProgressText.setText(this.logic.courseProgressStatisticFormatter(courseComboBox.getValue()));
        });

        // adding values to all courseComboboxes
        List<String> courses = this.courseLogic.retrieveCourseNames();
        // Adding courses to ComboBox.
        for (int i = 0; i < courses.size(); i++) {
            courseComboBox.getItems().add(courses.get(i));

        }
        view.add(returnBtn, 5, 0);
        view.add(averageProgressLabel, 1, 0);
        view.add(courseComboBox, 1, 1);
        view.add(averageProgressText, 1, 3);

        this.gui.goToNext(view, "Average progress");
    }

    private void progressPerModule() {
        // Third column of first row
        GridPane view = generateGrid();
        Label progressStudentInCourseLabel = new Label("Progress per module for student in course");
        ComboBox<String> courseComboBoxForStudentCourseProgress = new ComboBox<>();

        ComboBox<String> studentComboBox = new ComboBox<>();
        final String defaultStudentComboBoxValue = "Select a student";
        studentComboBox.setValue(defaultStudentComboBoxValue);
        Text studentProgressText = new Text();

        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());
        view.add(returnBtn, 5, 0);
        view.add(progressStudentInCourseLabel, 0, 1);
        view.add(courseComboBoxForStudentCourseProgress, 0, 2);
        view.add(studentProgressText, 1, 3);

        courseComboBoxForStudentCourseProgress.setOnAction(pickedCourse -> {
            List<String> studentEmails = this.enrollmentLogic
                    .retrieveEmailsFromCourse(courseComboBoxForStudentCourseProgress.getValue());
            for (int i = 0; i < studentEmails.size(); i++) {
                studentComboBox.getItems().add(studentEmails.get(i));

            }
            view.add(studentComboBox, 0, 3);
        });

        studentComboBox.setOnAction(pickedStudent -> {
            studentProgressText.setText(this.logic.progressOfStudentFormatter(studentComboBox.getValue(),
                    courseComboBoxForStudentCourseProgress.getValue()));
        });

        // adding values to all courseComboboxes
        List<String> courses = this.courseLogic.retrieveCourseNames();
        // Adding courses to ComboBox.
        for (int i = 0; i < courses.size(); i++) {

            courseComboBoxForStudentCourseProgress.getItems().add(courses.get(i));

        }

        this.gui.goToNext(view, "Progress per module");
    }

    private void Amountcertificates() {
        GridPane view = generateGrid();
        // Fourth column of first row
        Label studentCertificatesLabel = new Label("Student certificates");
        ComboBox<String> allStudentsBox = new ComboBox<>();
        List<String> allStudents = this.studentLogic.retrieveAllEmails();
        // adding students to ComboBox.
        for (int i = 0; i < allStudents.size(); i++) {
            allStudentsBox.getItems().add(allStudents.get(i));
        }
        Text allCertificatesText = new Text();

        allStudentsBox.setOnAction(pickedStudent -> {
            allCertificatesText.setText(
                    this.logic.certificateFormatter(this.logic.retrieveCertificates(allStudentsBox.getValue())));
        });
        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());
        view.add(returnBtn, 5, 0);
        view.add(studentCertificatesLabel, 1, 1);
        view.add(allStudentsBox, 1, 2);
        view.add(allCertificatesText, 1, 3);
        this.gui.goToNext(view, "Student certificates");
    }

    private void genderStatistics() {
        GridPane view = generateGrid();
        // First column of first row
        // Gender statistics
        Label genderStatistics = new Label("Gender Statistics");
        ComboBox<Gender> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().add(Gender.F);
        genderComboBox.getItems().add(Gender.M);
        genderComboBox.getItems().add(Gender.O);
        Text genderStatisticsValue = new Text();
        genderComboBox.setOnAction(pickedGender -> {
            genderStatisticsValue.setText(this.logic.genderStatisticsFormatter(genderComboBox.getValue()));
        });
        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());
        view.add(returnBtn, 5, 0);
        view.add(genderStatistics, 1, 1);
        view.add(genderComboBox, 1, 2);
        view.add(genderStatisticsValue, 1, 4);
        this.gui.goToNext(view, "Gender statistics");
    }

    private void top3CoursesWithCert() {
        GridPane view = generateGrid();

        // Second column of second row
        Label topCourseLabel = new Label("Top 3 courses by number of certificates");
        Text topCoursesText = new Text(this.logic.top3CoursesFormatted());

        // First column of second row
        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());
        view.add(returnBtn, 5, 0);
        view.add(topCourseLabel, 1, 1);
        view.add(topCoursesText, 1, 2);

        this.gui.goToNext(view, "Top 3 courses by number of certificates");
    }

    private void recommendedCourses() {
        GridPane view = generateGrid();
        // Third column of second row

        Label recommendedCoursesLabel = new Label("Reccomended Course");
        ComboBox<String> courseBoxForRecommendedCourses = new ComboBox<>();
        Text recommendedCoursesText = new Text();

        courseBoxForRecommendedCourses.setOnAction(pickedCourse -> {
            recommendedCoursesText
                    .setText(this.logic.recommendedCourseFormatter(courseBoxForRecommendedCourses.getValue()));
        });
        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());
        view.add(returnBtn, 5, 0);
        view.add(recommendedCoursesLabel, 1, 1);
        view.add(courseBoxForRecommendedCourses, 1, 2);

        view.add(recommendedCoursesText, 1, 4);
        // adding values to all courseComboboxes
        List<String> courses = this.courseLogic.retrieveCourseNames();
        // Adding courses to ComboBox.
        for (int i = 0; i < courses.size(); i++) {

            courseBoxForRecommendedCourses.getItems().add(courses.get(i));

        }
        this.gui.goToNext(view, "Recommended courses");
    }

    private void nrReceivedCertificates() {
        GridPane view = generateGrid();

        // Fourth column of second row

        Label numberOfReceivedCertificatesLabel = new Label("Number of received certificates");
        ComboBox<String> courseBoxForNumberOfCertificates = new ComboBox<>();
        Text numberOfCertificatesText = new Text();

        courseBoxForNumberOfCertificates.setOnAction(pickedCourse -> {
            numberOfCertificatesText
                    .setText(this.logic.numberOfCertificatesFormatter(courseBoxForNumberOfCertificates.getValue()));
        });
        Button returnBtn = new Button("Back");
        returnBtn.setOnMouseClicked(click -> createView());
        view.add(returnBtn, 5, 0);
        view.add(numberOfReceivedCertificatesLabel, 1, 1);
        view.add(courseBoxForNumberOfCertificates, 1, 2);
        view.add(numberOfCertificatesText, 1, 4);
        // adding values to all courseComboboxes
        List<String> courses = this.courseLogic.retrieveCourseNames();
        // Adding courses to ComboBox.
        for (int i = 0; i < courses.size(); i++) {
            courseBoxForNumberOfCertificates.getItems().add(courses.get(i));

        }
        this.gui.goToNext(view, "Number of received certificates");
    }

}
