package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import logic.CourseLogic;

//Class responsible for all the views that are related to the management of courses
public class CourseManagementView extends View {
    private CourseLogic logic;

    public CourseManagementView(GUI baseUI) {
        super(baseUI);
        this.logic = new CourseLogic();
    }

    // The following method creates the first view that the user sees when going
    // into course management
    @Override
    public void createView() {
        // Initial layout setup
        GridPane view = generateGrid();

        // UI components
        Label createLabel = new Label("Create a course");
        Button addCourseBtn = new Button("+");
        addCourseBtn.setOnMouseClicked(clicked -> addCourseView());

        // Dropdown showing existing courses
        Label selectLabel = new Label("Select an existing course");
        ComboBox<String> dropdown = new ComboBox<>();
        Button editBtn = new Button("Edit");
        Button addModuleToCourseBtn = new Button("Add module");
        Button deleteModuleFromCourseBtn = new Button("Delete module");
        Button addRecommendedCourseBtn = new Button("Add recommended course");
        Button deleteBtn = new Button("Delete");

        // Futher layout setup
        view.add(createLabel, 1, 0);
        view.add(addCourseBtn, 1, 1);
        view.add(selectLabel, 0, 0);
        view.add(dropdown, 0, 1);
        view.add(editBtn, 0, 2);
        view.add(addModuleToCourseBtn, 0, 3);
        view.add(deleteModuleFromCourseBtn, 0, 4);
        view.add(addRecommendedCourseBtn, 0, 5);
        view.add(deleteBtn, 0, 6);

        activate(view, "Course management");
    }

    private void addCourseView() {

    }
}
