package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import domain.Student;
import domain.Module;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.StudentLogic;
import logic.enrollLogic;

class StudentManagementView extends View {
    private StudentLogic logic;
    private enrollLogic enrollLogic;

    StudentManagementView(GUI gui) {
        super(gui);
        this.logic = new StudentLogic();
        this.enrollLogic = new enrollLogic();
    }

    @Override
    protected void createView() {

        GridPane view = generateGrid();

        // Setting up the dropwon which contains Student identifiers
        Label editStudentLabel = new Label("Select student:");
        ComboBox<String> studentList = new ComboBox<>();
        final String defaultStudentValue = "Select student";
        studentList.setValue(defaultStudentValue);
        studentList.getItems().add(defaultStudentValue);

        // Retrieves the list of Student names from database in combination with their
        // key (mail address) in a map
        Map<String, String> studentMap = this.logic.getNameAndEmail();
        Collection<String> list = studentMap.keySet();
        studentList.getItems().addAll(list);

        // Buttons for possible actions on a selected student from the dropdown
        Button editStudentButton = new Button("Edit student");
        Button deleteStudentButton = new Button("Delete student");
        Button contentProgressButton = new Button("View and update module/webcast progress");
        Button enrollmentToCourseBtn = new Button("Enroll student in Course");
        // Second column
        Label createStudentLabel = new Label("Create student:");
        // The plus sign will lead you to the create new student view.
        Button createStudentButton = new Button("+");
        createStudentButton.setOnMouseClicked(clicked -> addStudentView());
        // Layout of the manage view
        view.add(editStudentLabel, 0, 0);
        view.add(studentList, 0, 1);
        view.add(editStudentButton, 0, 2);
        view.add(deleteStudentButton, 0, 3);
        view.add(contentProgressButton, 0, 4);
        view.add(enrollmentToCourseBtn, 0, 5);
        view.add(createStudentLabel, 1, 0);
        view.add(createStudentButton, 1, 1);

        // Switchting to the edit Student view if a user clicks 'edit'. Checks if a
        // Student is selected
        editStudentButton.setOnMouseClicked(clicked -> {
            // It takes the value of the chosen key from the combobox
            // This value is the email.
            // Sends it to the logic class and picks the student from the database
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label("Not selected anything"), 0, 6);
                return;
            }
            Student pickedStudent = this.logic.getStudentByEmail(studentMap.get(studentList.getValue()));
            editStudentView(pickedStudent);
        });

        // Prompting the delete Student functionality if the user clicks on the delete
        // button while a Student is selected
        deleteStudentButton.setOnMouseClicked(clicked -> {
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label("Not selected anything"), 0, 6);
                return;
            }

            String chosenStudentEmail = studentMap.get(studentList.getValue());
            this.logic.deleteStudentByEmail(chosenStudentEmail);
            succesfullProcesView();
        });

        // Switchting to ContentProgress view if a user clicks 'Content progress'.
        // Checks if a
        // Student is selected
        contentProgressButton.setOnMouseClicked(clicked -> {
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label("Not selected anything"), 0, 6);
                return;
            }

            Student student = this.logic.getStudentByEmail(studentMap.get(studentList.getValue()));
            contentProgressView(student);

        });

        enrollmentToCourseBtn.setOnMouseClicked(clicked -> {
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label("Not selected anything"), 0, 6);
                return;
            }
            String studentEmail = studentMap.get(studentList.getValue());
            enrollStudentView(studentEmail);
        });

        // See abstract class "View"
        activate(view, "Student management");

    }

    // View responsible for showing the progress within ContentItems for the
    // selected student, and making that progress updatable for the user
    private void contentProgressView(Student student) {
        /*
         * Retrieving a map of all Modules in Course to which a Student is enrolled.
         * Each Module is (as instantiated form) the key of the value, which is the
         * progression amount 1-100
         */
        Map<Module, Integer> moduleProgressMap = this.logic.receiveModuleProgressForStudent(student);

        // Setting a list of all CourseNames in which a Student is enrolled, via the map
        // created above
        ArrayList<String> courseNames = new ArrayList<>();
        for (Module module : moduleProgressMap.keySet()) {
            String courseName = module.getRelatedCourseName();
            if (!courseNames.contains(courseName)) {
                courseNames.add(courseName);
            }
        }

        // Initial layout setup
        GridPane view = generateFormGrid();

        // View title for use
        Label welcomeLabel = new Label("View and update progress of : " + student.getStudentName());
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeLabel, 0, 0, 2, 1);
        GridPane.setHalignment(welcomeLabel, HPos.LEFT);
        GridPane.setMargin(welcomeLabel, new Insets(20, 0, 20, 0));

        // Dropdown with all courses the Student is enrolled in
        Label courseDropdownLabel = new Label("Courses:");
        ComboBox<String> courseDropdown = new ComboBox<>();
        courseDropdown.getItems().add("-");

        for (String courseName : courseNames) {
            courseDropdown.getItems().add(courseName);
        }

        /**
         * Of the selected Course in the Course dropdown, show all modules in another
         * dropdown which are related to that course. The modules are ordered by their
         * position within the course
         **/
        Label moduleDropdownLabel = new Label("Select a course first!");
        ComboBox<Module> moduleDropdown = new ComboBox<>();

        courseDropdown.setOnAction(courseSelected -> {
            moduleDropdown.getItems().clear();
            String selectedCourseName = courseDropdown.getValue();

            // Each related module to the current selected Course by the user, gets put into
            // the module dropdown
            int count = 0;
            for (Module module : moduleProgressMap.keySet()) {
                if (module.getRelatedCourseName().equals(selectedCourseName)) {
                    moduleDropdown.getItems().add(module);
                    count++;
                }
            }

            if (count == 0) {
                moduleDropdownLabel.setText("-no module(s) linked with selected course-");
            } else if (count == 1) {
                moduleDropdownLabel.setText("1 module linked with course: ");

            } else {
                moduleDropdownLabel
                        .setText(count + " modules available (ordered by their position within course, set by you!):");
            }
        });

        // Progress visualization using a slide and indicators
        final Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        final ProgressBar pb = new ProgressBar(0);
        final ProgressIndicator pi = new ProgressIndicator(0);
        Group progressVisualization = new Group();
        progressVisualization.getChildren().addAll(slider, pb, pi);
        Button updateProgressButton = new Button("Update progress");
        Label updateProgressLabel = new Label("");
        updateProgressLabel.setTextFill(Color.GREEN);
        updateProgressButton.setVisible(false);

        // Making the progression updatable by the user, by the slider
        slider.valueProperty().addListener(
                (ObservableValue<? extends Number> ov, Number oldValue,
                        Number newValue) -> {
                    updateProgressLabel.setText("");
                    pb.setProgress(newValue.doubleValue() / 100);
                    pi.setProgress(newValue.doubleValue() / 100);
                });

        // Every time a new module is selected, the progress indicators and sliders get
        // the current value from the module
        moduleDropdown.setOnAction(moduleSelected -> {
            updateProgressLabel.setText("");
            Module selectedModule = moduleDropdown.getValue();
            if (selectedModule == null) {
                updateProgressButton.setVisible(false);
                return;
            }

            int progressionPercentage = moduleProgressMap.get(selectedModule);
            slider.setValue(progressionPercentage);
            updateProgressButton.setVisible(true);
        });

        // Action event for the progress update button, so that progress of the selected
        // module gets updated based on the progress indicator (set by user)
        updateProgressButton.setOnMouseClicked(clicked -> {
            int moduleContentID = moduleDropdown.getValue().getID();
            int newProgressAmount = (int) Math.round(slider.getValue());

            this.logic.updateProgressContenItem(moduleContentID, student, newProgressAmount);
            moduleProgressMap.replace(moduleDropdown.getValue(), newProgressAmount);

            updateProgressLabel.setText("Updated!");

        });

        // Final setup and activation
        view.add(courseDropdownLabel, 0, 1);
        view.add(courseDropdown, 0, 2);
        view.add(moduleDropdownLabel, 1, 1);
        view.add(moduleDropdown, 1, 2);
        view.add(slider, 1, 3);
        view.add(pb, 1, 4);
        view.add(pi, 1, 5);
        view.add(updateProgressButton, 2, 3);
        view.add(updateProgressLabel, 2, 4);

        activate(view, "Content progress of Student: " + student.getStudentName());

    }

    private void addStudentView() {
        GridPane view = generateFormGrid();
        Label title = new Label("Create new student");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        // Labels
        Label nameLabel = new Label("Name");
        Label genderBoxLabel = new Label("Gender");
        Label emailLabel = new Label("Email address");
        Label dateOfBirthLabel = new Label("Date of birth");
        Label addressLabel = new Label("Address");
        Label countryLabel = new Label("Country");
        Label cityLabel = new Label("City");

        // TextFields
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField countryField = new TextField();
        TextField cityField = new TextField();
        // Dropdown / Radio buttons

        // Combobox holding genders
        ComboBox genderBox = new ComboBox<>();
        final String defaultChoice = "O";
        genderBox.getItems().addAll("O", "M", "F");
        genderBox.setValue(defaultChoice);

        // These three inputfields make up the date of birth
        TextField dayField = new TextField();
        TextField monthField = new TextField();
        TextField yearField = new TextField();

        // Together with the right label, are the inputfields for day, month and year
        // stored in a VBOX;
        VBox days = new VBox(new Label("Day"), dayField);
        VBox month = new VBox(new Label("Month"), monthField);
        VBox year = new VBox(new Label("Year"), yearField);
        // These three VBoxes are put in a HBox;
        HBox birthdayBox = new HBox(days, month, year);
        birthdayBox.setSpacing(25);

        // Street, Postalcode and Housenumber make up the addres
        TextField street = new TextField();
        TextField postalCode = new TextField();
        TextField houseNr = new TextField();

        // The three textfields are put in these three Vboxes
        VBox streetBox = new VBox(new Label("Street"), street);
        VBox houseNumber = new VBox((new Label("House number")), houseNr);
        VBox postalBox = new VBox(new Label("Postalcode"), postalCode);
        // These VBOXES are put in a HBOX
        HBox addresInformation = new HBox(streetBox, houseNumber, postalBox);
        addresInformation.setSpacing(25);

        // Warning labels
        Text warningDateOfBirth = new Text("");
        Text warningEmail = new Text("");
        Text lastWarningText = new Text("");

        // Layout first column
        view.add(title, 1, 0, 2, 1);
        view.add(nameLabel, 0, 1);
        view.add(genderBoxLabel, 0, 2);
        view.add(emailLabel, 0, 3);
        view.add(dateOfBirthLabel, 0, 4);
        view.add(addressLabel, 0, 5);
        view.add(countryLabel, 0, 6);
        view.add(cityLabel, 0, 7);
        // secondColumn
        view.add(nameField, 1, 1);
        view.add(genderBox, 1, 2);
        view.add(emailField, 1, 3);
        view.add(birthdayBox, 1, 4);
        view.add(addresInformation, 1, 5);
        view.add(countryField, 1, 6);
        view.add(cityField, 1, 7);
        view.add(warningDateOfBirth, 2, 4);
        view.add(warningEmail, 2, 3);
        // Button to submit and go to succesfull screen
        Button studentCreationButton = new Button("Create student");
        view.add(studentCreationButton, 1, 9);
        view.add(lastWarningText, 2, 9);

        // Eventhandler

        studentCreationButton.setOnMouseClicked(clicked -> {
            // It calls the method datOfBirthIsValid in order if the date of birth is valid
            // It checks if the date is not further than now
            // It also checks if the date of birth textFields are filled.
            boolean dateValid = logic.dateOfBirthIsValid(dayField.getText(), monthField.getText(),
                    yearField.getText());
            boolean addresValid = logic.addresIsValid(street.getText(), houseNr.getText(), postalCode.getText());
            // Checks if inputfields are not empty
            boolean nameIsFilled = logic.fieldIsNotEmpty(nameField.getText());
            boolean emailIsValid = logic.validateMailAddress(emailField.getText());
            boolean cityIsValid = logic.fieldIsNotEmpty(cityField.getText());
            boolean countryIsFilled = logic.fieldIsNotEmpty(countryField.getText());
            // if the date is not valid, than it will show a warning
            if (!(dateValid)) {
                warningDateOfBirth.setText("Date of birth is invalid");
            }

            // If email already exist, than it shows a warning
            if (logic.emailExist(emailField.getText())) {
                warningEmail.setText("Email already exist!");
                return;
            }

            // If every boolean value is true, than it will insert the new student to the
            // logic to create a new student and will be send to the database
            // NOTE: When tested, I changed my database in order put housenumber and street
            // seperately
            if (nameIsFilled && emailIsValid && cityIsValid && countryIsFilled && dateValid && addresValid) {
                this.logic.newStudent(nameField.getText(), emailField.getText(),
                        genderBox.getValue().toString(),
                        dayField.getText(), monthField.getText(), yearField.getText(),
                        street.getText(), houseNr.getText(), postalCode.getText(),
                        countryField.getText(), cityField.getText());
                // When succesfully inserted, it will go to the succesfullprocesView
                succesfullProcesView();
            } else {
                lastWarningText.setText("one of the input values are invalid");
            }
        });
        activate(view, "Add student");

    }

    private void editStudentView(Student studentToEdit) {
        GridPane view = generateFormGrid();
        // In case we want to edit his email
        Label welcomeToFormLabel = new Label("Editing: '" + studentToEdit.getStudentName() + "'");
        welcomeToFormLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeToFormLabel, 1, 0, 2, 1);
        // Gender combobox
        ComboBox genderBox = new ComboBox<>();
        genderBox.setValue(studentToEdit.getGender().toString());
        genderBox.getItems().addAll("O", "F", "M");
        TextField nameField = new TextField(studentToEdit.getStudentName());

        // input fields filled with information of the student
        TextField dayField = new TextField((Integer.toString(studentToEdit.getDateOfBirth().getDayOfMonth())));
        TextField monthField = new TextField((Integer.toString(studentToEdit.getDateOfBirth().getMonthValue())));
        TextField yearField = new TextField((Integer.toString(studentToEdit.getDateOfBirth().getYear())));
        // Fields filled with address information
        TextField streetField = new TextField(studentToEdit.getstreet());
        TextField houseNumber = new TextField(Integer.toString(studentToEdit.getHouseNumber()));
        TextField postalCodeField = new TextField(studentToEdit.getPostalCode());
        TextField countryField = new TextField(studentToEdit.getCountry());
        TextField cityField = new TextField(studentToEdit.getCity());

        // Vertical box for date of birth
        VBox days = new VBox(new Label("Day"), dayField);
        VBox month = new VBox(new Label("Month"), monthField);
        VBox year = new VBox(new Label("Year"), yearField);
        // Horizontal box to put all of the VBoxes together
        HBox birthdayBox = new HBox(days, month, year);
        birthdayBox.setSpacing(25);

        // Verical boxes for addres information(street, housenumber, postalcode)
        VBox street = new VBox((new Label("Street")), streetField);
        VBox house = new VBox((new Label("Housenumber")), houseNumber);
        VBox postal = new VBox((new Label("Postalcode")), postalCodeField);
        // Horizontal box to put all the addres information together
        HBox addres = new HBox(street, house, postal);
        addres.setSpacing(25);
        // Layout first column
        view.add(new Label("Name"), 0, 1);
        view.add(new Label("Gender"), 0, 2);
        view.add(new Label("Date of birth"), 0, 3);
        view.add(new Label("Address"), 0, 4);
        view.add(new Label("Country"), 0, 5);
        view.add(new Label("City"), 0, 6);
        // Layout second column
        view.add(nameField, 1, 1);
        view.add(genderBox, 1, 2);
        view.add(birthdayBox, 1, 3);
        view.add(addres, 1, 4);
        view.add(countryField, 1, 5);
        view.add(cityField, 1, 6);
        // Button to submit changes
        Button updateButton = new Button("Update student");
        view.add(updateButton, 1, 7);

        Text lastWarningText = new Text("");
        view.add(lastWarningText, 2, 7);
        updateButton.setOnMouseClicked(clicked -> {
            // Validate inputfields date of birth and addres
            // It also checks if the date of birth or addresfields are blank
            boolean dateValid = logic.dateOfBirthIsValid(dayField.getText(), monthField.getText(),
                    yearField.getText());
            boolean addresValid = logic.addresIsValid(streetField.getText(), houseNumber.getText(),
                    postalCodeField.getText());
            // Checks if inputfields are not empty
            boolean nameIsFilled = logic.fieldIsNotEmpty(nameField.getText());
            boolean cityIsValid = logic.fieldIsNotEmpty(cityField.getText());
            boolean countryIsFilled = logic.fieldIsNotEmpty(countryField.getText());
            // Checks if the boolean values are true
            if (nameIsFilled && cityIsValid && countryIsFilled && dateValid && addresValid) {
                System.out.println("Succesfull");
                this.logic.updateStudent(studentToEdit, nameField.getText(),
                        yearField.getText(), monthField.getText(), dayField.getText(), genderBox.getValue().toString(),
                        streetField.getText(), cityField.getText(), countryField.getText(),
                        houseNumber.getText(), postalCodeField.getText());

                succesfullProcesView();
            } else {
                lastWarningText.setText("one of the input values are invalid");
            }
        });
        activate(view, "Edit student information");
    }

    private void enrollStudentView(String studentEmail) {
        GridPane view = generateGrid();
        Label title = new Label("Enrollment of " + studentEmail);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Label warning = new Label("");

        ComboBox boxes = new ComboBox<>();
        final String defaultValue = "Select course";
        boxes.setValue(defaultValue);
        List<String> courseList = this.enrollLogic.getCourseNames();
        boxes.getItems().addAll(courseList);

        Button sumbitEnrollBtn = new Button("Enroll student");

        view.add(title, 0, 0);
        view.add(boxes, 0, 1);
        view.add(sumbitEnrollBtn, 0, 2);

        sumbitEnrollBtn.setOnMouseClicked(clicked -> {
            if (boxes.getValue().equals(defaultValue)) {
                warning.setText("Choose course");
                return;
            }
            Student student = this.logic.getStudentByEmail(studentEmail);
            this.enrollLogic.enrollStudentToCourse(student, boxes.getValue().toString());
        });

        activate(view, "Enroll student");
    }

    private void succesfullProcesView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully processed");
        Button homeBtn = new Button("Home");
        Button resumeBtn = new Button("Resume");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(resumeBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        resumeBtn.setOnMouseClicked(clicked -> new StudentManagementView(this.gui).createView());
        activate(view, "Successful proces");
    }

}
