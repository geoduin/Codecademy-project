package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StudentManageView extends View {

    protected StudentManageView(GUI gui) {
        super(gui);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void createView() {

        GridPane view = generateGrid();

        // First column
        Label editStudentLabel = new Label("Select student:");
        ComboBox studentList = new ComboBox<>();
        final String defaultStudentValue = "Select student";
        studentList.setValue(defaultStudentValue);
        // Retrieves the list of studentnames from database

        // Based on the value of the combobox value, it will either delete or edit the
        // student information.
        Button editStudentButton = new Button("Edit student");
        Button deleteStudent = new Button("Delete student");
        Button readAllStudent = new Button("Show all students");

        // Second column
        Label createStudentLabel = new Label("Create student:");
        // The plus sign will lead you to the create new student view.
        Button createStudentButton = new Button("+");

        // Layout of the manage view
        view.add(editStudentLabel, 0, 0);
        view.add(studentList, 0, 1);
        view.add(editStudentButton, 0, 2);
        view.add(deleteStudent, 0, 3);
        view.add(readAllStudent, 0, 4);
        view.add(createStudentLabel, 1, 0);
        view.add(createStudentButton, 1, 1);

        // See abstract class "View"
        activate(view, "Student management");

    }

}
