package gui;

import java.util.HashMap;

import domain.Course;
import domain.Difficulty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.CourseLogic;
import logic.ModuleLogic;

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
        final String defaultDropdownValue = "-no course selected-";
        dropdown.setValue(defaultDropdownValue);
        dropdown.getItems().add(defaultDropdownValue);
        // Porting all the course names to the dropdown
        for (String courseName : this.logic.retrieveCourseNames()) {
            dropdown.getItems().add(courseName);
        }

        // Options for user with existing course
        Button editBtn = new Button("Edit");
        Button addModuleToCourseBtn = new Button("Add module");
        Button deleteModuleFromCourseBtn = new Button("Delete module");
        Button addRecommendedCourseBtn = new Button("Add recommended course");
        Button deleteBtn = new Button("Delete the course");
        // Button logic
        // Delete course
        deleteBtn.setOnMouseClicked(clicked -> {
            if (dropdown.getValue().equals(defaultDropdownValue)) {
                view.add(new Label("No Course selected!"), 0, 7);
            } else {
                this.logic.deleteCourse(dropdown.getValue());
                successfullyDeletedView();
            }
        });
        // Edit course
        editBtn.setOnMouseClicked(clicked -> {
            if (dropdown.getValue().equals(defaultDropdownValue)) {
                view.add(new Label("No Course selected!"), 0, 7);
            } else {
                // getting the to-be-edited module
                String courseToDelete = dropdown.getValue();
                editModuleView(courseToDelete);
            }
        });

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

    // Creating a view for the user to create a new course
    private void addCourseView() {
        // Initial layout setup
        GridPane view = generateFormGrid();

        // form label
        Label welcomeToFormLabel = new Label("Create a course");
        welcomeToFormLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeToFormLabel, 0, 0, 2, 1);
        GridPane.setHalignment(welcomeToFormLabel, HPos.LEFT);
        GridPane.setMargin(welcomeToFormLabel, new Insets(20, 0, 20, 0));

        // Course name input
        Label courseNameLabel = new Label("Course name:");
        TextField courseNameField = new TextField();
        view.add(courseNameLabel, 0, 1);
        view.add(courseNameField, 1, 1);

        // name input
        Label courseTopicLabel = new Label("Course topic:");
        TextField courseTopicField = new TextField();
        view.add(courseTopicLabel, 0, 2);
        view.add(courseTopicField, 1, 2);

        // Description input
        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();
        view.add(descriptionLabel, 0, 3);
        view.add(descriptionField, 1, 3);

        // Course difficulty input
        Label courseDifficultyLabel = new Label("Course difficulty:");
        ComboBox<Difficulty> dropdown = new ComboBox<>();
        Text noValueSelectedError = new Text("");
        noValueSelectedError.setFill(Color.FIREBRICK);
        // Setting all dropdown options
        dropdown.getItems().add(Difficulty.EASY);
        dropdown.getItems().add(Difficulty.MEDIUM);
        dropdown.getItems().add(Difficulty.HARD);
        view.add(courseDifficultyLabel, 0, 4);
        view.add(dropdown, 1, 4);
        view.add(noValueSelectedError, 2, 4);

        // Dropdown of modules, of which one needs to be added to the new course
        // (opdrachtbeschrijving requirement)
        Label addModuleLabel = new Label("Add a module to course:");
        ComboBox<String> modulesDropdown = new ComboBox<>();
        Text noModuleSelectedError = new Text("");
        noModuleSelectedError.setFill(Color.FIREBRICK);
        // Retrieving formatted module strings and adding them to dropdown
        HashMap<String, Integer> moduleNameVersionAndIDPairs = new ModuleLogic().getAddableModules();
        for (String moduleNameAndVersion : moduleNameVersionAndIDPairs.keySet()) {
            modulesDropdown.getItems().add(moduleNameAndVersion);
        }
        // adding dropdown to view
        view.add(addModuleLabel, 0, 5);
        view.add(modulesDropdown, 1, 5);
        view.add(noModuleSelectedError, 2, 5);

        // Create course button
        Button createButton = new Button("Create course");
        createButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        view.add(createButton, 1, 6);

        // Error to show when not all fields have an input
        Text nullOrAlreadyExistsErrorField = new Text("");
        nullOrAlreadyExistsErrorField.setFill(Color.FIREBRICK);
        view.add(nullOrAlreadyExistsErrorField, 1, 7);

        // Input validation when user presses 'create'
        createButton.setOnMouseClicked(clicked -> {
            // check if any field has no input
            if (hasNoInput(courseNameField) || hasNoInput(courseTopicField) || hasNoInput(descriptionField)) {
                nullOrAlreadyExistsErrorField.setText("One or more fields are not filled!");
                return;
            } else {
                nullOrAlreadyExistsErrorField.setText("");
            }

            // checking if dropdown has no selected value
            if (dropdown.getValue() == null) {
                noValueSelectedError.setText("No value selected!");
                return;
            } else {
                noValueSelectedError.setText("");
            }

            if (modulesDropdown.getValue() == null) {
                noModuleSelectedError.setText("No module selected, create one if needed!");
                return;
            } else {
                noModuleSelectedError.setText("");
            }

            // If the whole input validation is done, the module gets created if it
            // doesn't exist yet.
            String courseName = courseNameField.getText();
            if (this.logic.retrieveCourse(courseName) != null) {
                nullOrAlreadyExistsErrorField.setText(courseName + " already exists!");
                return;
            }

            // Creating the course
            this.logic.newCourse(courseName, courseTopicField.getText(), descriptionField.getText(),
                    dropdown.getValue(), moduleNameVersionAndIDPairs.get(modulesDropdown.getValue()));
            courseSuccessfullyCreatedView();

        });
        activate(view, "Create course");

    }

    // Creating a view for the user to edit a module
    public void editModuleView(String courseName) {
        GridPane view = generateFormGrid();
        Course course = this.logic.retrieveCourse(courseName);

        // form label
        Label welcomeToFormLabel = new Label("Editing: '" + courseName + "'");
        welcomeToFormLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeToFormLabel, 0, 0, 2, 1);
        GridPane.setHalignment(welcomeToFormLabel, HPos.LEFT);
        GridPane.setMargin(welcomeToFormLabel, new Insets(20, 0, 20, 0));

        // Edit the topic
        Label topicLabel = new Label("Topic:");
        TextField topicField = new TextField(course.getTopic());
        view.add(topicLabel, 0, 1);
        view.add(topicField, 1, 1);

        // Edit the description
        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField(course.getDescription());
        view.add(descriptionLabel, 0, 2);
        view.add(descriptionField, 1, 2);

        // Edit the difficulty
        Label courseDifficultyLabel = new Label("Course difficulty:");
        ComboBox<Difficulty> dropdown = new ComboBox<>();
        dropdown.setValue(course.getDifficulty());
        // Setting all dropdown options
        dropdown.getItems().add(Difficulty.EASY);
        dropdown.getItems().add(Difficulty.MEDIUM);
        dropdown.getItems().add(Difficulty.HARD);
        view.add(courseDifficultyLabel, 0, 3);
        view.add(dropdown, 1, 3);

        // And finally the submit button
        Button editBtn = new Button("Edit course");
        editBtn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        view.add(editBtn, 0, 4);

        Text nullOrAlreadyExistsErrorField = new Text("");
        nullOrAlreadyExistsErrorField.setFill(Color.FIREBRICK);
        view.add(nullOrAlreadyExistsErrorField, 1, 5);

        editBtn.setOnMouseClicked((clicked -> {
            if (hasNoInput(descriptionField) || hasNoInput(topicField)) {
                nullOrAlreadyExistsErrorField.setText("One or more fields are not filled!");
                return;
            } else {
                nullOrAlreadyExistsErrorField.setText("");
            }

            this.logic.editCourse(topicField.getText(), descriptionField.getText(), dropdown.getValue(), course);
            courseSuccessfullyEditedView();

        }));
        activate(view, "Edit module");
    }

    // When a module is edited, this method creates a view to give the user a
    // general menu
    private void courseSuccessfullyEditedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully Edited!");
        Button homeBtn = new Button("Go home");
        Button editAnotherCourseBtn = new Button("Edit other");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(editAnotherCourseBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        editAnotherCourseBtn.setOnMouseClicked(clicked -> new CourseManagementView(this.gui).createView());
        activate(view, "Successfully edited!");
    }

    // When a course is created, this method creates a view to give the user a
    // general menu
    private void courseSuccessfullyCreatedView() {

        GridPane view = generateGrid();
        Label label = new Label("Successfully created!");
        Button homeBtn = new Button("Go home");
        Button createOtherCourseBtn = new Button("Create more");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(createOtherCourseBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        createOtherCourseBtn.setOnMouseClicked(clicked -> new CourseManagementView(this.gui).addCourseView());

        activate(view, "Successfully created!");

    }

    // When a course is deleted, this method creates a view to give the user a
    // general menu
    private void successfullyDeletedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully deleted!");
        Button homeBtn = new Button("Go home");
        Button backBtn = new Button("Go back");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(backBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        backBtn.setOnMouseClicked(clicked -> new CourseManagementView(this.gui).createView());
        activate(view, "Successfully deleted");
    }
}
