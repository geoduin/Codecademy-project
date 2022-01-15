package gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import domain.Student;
import domain.Enrollment;
import domain.Gender;
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
import javafx.scene.control.Separator;
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
import logic.EnrollmentLogic;
import logic.InputValidation;

class StudentManagementView extends View {
    private StudentLogic logic;
    private EnrollmentLogic enrollmentLogic;

    StudentManagementView(GUI gui) {
        super(gui);
        this.logic = new StudentLogic();
        this.enrollmentLogic = new EnrollmentLogic();
    }

    @Override
    protected void createView() {
        GridPane view = generateGrid();

        // Setting up the dropwon which contains Student identifiers
        Label editStudentLabel = new Label("Select student:");
        final String noValueSelectedMSG = "No student selected!";
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
        Button editStudentButton = new Button("Edit");
        editStudentButton.setId("editButton");
        Button deleteStudentButton = new Button("Delete");
        deleteStudentButton.setId("deleteButton");
        Button contentProgressButton = new Button("Update progress");
        contentProgressButton.setId("editButton");
        Button enrollToCourseButton = new Button("Enroll to Course");
        enrollToCourseButton.setId("secondAddBtn");
        Button deleteEnrollmentButton = new Button("Delete enrollment");
        deleteEnrollmentButton.setId("deleteButton");

        // Second column
        Label createStudentLabel = new Label("Create student:");
        // The plus sign will lead you to the create new student view.
        Button createStudentButton = new Button("+");
        createStudentButton.setId("addButton");
        createStudentButton.setOnMouseClicked(clicked -> addStudentView());
        // Layout of the manage view
        view.add(editStudentLabel, 0, 0);
        view.add(studentList, 0, 1);
        view.add(editStudentButton, 0, 2);
        view.add(contentProgressButton, 0, 3);
        view.add(enrollToCourseButton, 0, 4);
        view.add(deleteStudentButton, 0, 5);
        view.add(deleteEnrollmentButton, 0, 6);

        view.add(createStudentLabel, 1, 0);
        view.add(createStudentButton, 1, 1);

        // Switchting to the edit Student view if a user clicks 'edit'. Checks if a
        // Student is selected
        editStudentButton.setOnMouseClicked(clicked -> {
            // It takes the value of the chosen key from the combobox
            // This value is the email.
            // Sends it to the logic class and picks the student from the database
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label(noValueSelectedMSG), 0, 7);
                return;
            }
            Student pickedStudent = this.logic.getStudentByEmail(studentMap.get(studentList.getValue()));
            editStudentView(pickedStudent);
        });

        // Prompting the delete Student functionality if the user clicks on the delete
        // button while a Student is selected
        deleteStudentButton.setOnMouseClicked(clicked -> {
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label(noValueSelectedMSG), 0, 7);
                return;
            }

            String chosenStudentEmail = studentMap.get(studentList.getValue());
            this.logic.deleteStudentByEmail(chosenStudentEmail);
            successfullyProcessView();
        });

        // Switchting to ContentProgress view if a user clicks 'Content progress'.
        // Checks if a
        // Student is selected
        contentProgressButton.setOnMouseClicked(clicked -> {
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label(noValueSelectedMSG), 0, 7);
                return;
            }

            Student student = this.logic.getStudentByEmail(studentMap.get(studentList.getValue()));
            contentProgressView(student);

        });

        enrollToCourseButton.setOnMouseClicked(clicked -> {
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label(noValueSelectedMSG), 0, 7);
                return;
            }
            String studentEmail = studentMap.get(studentList.getValue());
            enrollStudentView(studentEmail);
        });

        // With the selected Student from the dropdown, go into a view were a dropdown
        // of removable enrollments is given
        deleteEnrollmentButton.setOnMouseClicked(clicked -> {
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label(noValueSelectedMSG), 0, 7);
                return;
            }
            String studentEmail = studentMap.get(studentList.getValue());
            deleteEnrollmentView(studentEmail);
        });

        // See abstract class "View"
        activate(view, "Student management");
    }

    // View containing a dropdown of removable enrollments, which the user can
    // select from to remove an enrollment. Certificates are automatically removed
    private void deleteEnrollmentView(String studentEmail) {
        // Initial layout setup
        GridPane view = generateGrid();

        // UI components
        Label dropdownLabel = new Label("Delete enrollments (any certificates are automatically deleted)");
        ComboBox<Enrollment> enrollmentsDropdown = new ComboBox<>();
        Button deleteBtn = new Button("Delete");
        deleteBtn.setId("deleteButton");
        Label errorLabel = new Label("No enrollment selected!");
        Label removedSuccessfullyLabel = new Label("Successfully removed!");
        removedSuccessfullyLabel.setVisible(false);
        errorLabel.setVisible(false);

        // Setting up the dropdown
        int count = 0;
        for (Enrollment enrollment : this.enrollmentLogic.getsEnrollmentsOfStudent(studentEmail)) {
            enrollmentsDropdown.getItems().add(enrollment);
            count++;
        }
        enrollmentsDropdown.setPromptText(count + " enrollment(s)");

        // If the user presses the delete button, the selected enrollment will be
        // removed
        deleteBtn.setOnMouseClicked(clicked -> {
            removedSuccessfullyLabel.setVisible(false);

            // Discontinue if the user did not select anything else than the prompt text
            if (enrollmentsDropdown.getValue() == null) {
                errorLabel.setVisible(true);
                return;
            }

            errorLabel.setVisible(false);
            Enrollment enrollmentToDelete = enrollmentsDropdown.getValue();
            this.enrollmentLogic.deleteEnrollment(enrollmentToDelete);
            enrollmentsDropdown.getItems().remove(enrollmentToDelete);
            removedSuccessfullyLabel.setVisible(true);
        });

        // Layout finalization and activation
        view.add(dropdownLabel, 0, 0);
        view.add(enrollmentsDropdown, 0, 1);
        view.add(deleteBtn, 0, 2);
        view.add(errorLabel, 0, 3);
        view.add(removedSuccessfullyLabel, 0, 4);

        activate(view, "Delete enrollments of student: " + studentEmail);

    }

    // View responsible for showing ContentItem (Module, Webcast) progress for the
    // selected student, and making that progress updatable for the user
    private void contentProgressView(Student student) {
        /*
         * Retrieving a map of all Modules in Course to which a Student is enrolled.
         * Each Module is (in instantiated form) the key of the value, which is the
         * progression amount between 1-100
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
        welcomeLabel.setId("title");
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
        Button updateProgressButton = new Button("Update progress");
        Label updateProgressLabel = new Label("");
        updateProgressLabel.setTextFill(Color.GREEN);
        updateProgressButton.setVisible(false);

        // Making the progression updatable by the user, by the slider
        slider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    updateProgressLabel.setText("");
                    pb.setProgress(newValue.doubleValue() / 100);
                    pi.setProgress(newValue.doubleValue() / 100);
                });

        // Every time another module is selected, the progress indicators and sliders
        // get
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

            this.logic.updateProgressContentItem(moduleContentID, student, newProgressAmount);
            moduleProgressMap.replace(moduleDropdown.getValue(), newProgressAmount);

            updateProgressLabel.setText("Updated!");
            updateProgressButton.setVisible(false);

        });

        // Setting up the view with the elements for Module progress
        view.add(courseDropdownLabel, 0, 1);
        view.add(courseDropdown, 0, 2);
        view.add(moduleDropdownLabel, 1, 1);
        view.add(moduleDropdown, 1, 2);
        view.add(slider, 1, 3);
        view.add(pb, 1, 4);
        view.add(pi, 1, 5);

        view.add(updateProgressButton, 2, 3);
        view.add(updateProgressLabel, 2, 4);

        /*
         * Next up, are the components to make it possible to view webcast progress and
         * add new webcasts for a student.
         */

        // Visually separating the Module and Webcast UI components
        view.add(new Separator(), 0, 6);
        view.add(new Separator(), 1, 6);
        view.add(new Separator(), 2, 6);

        /*
         * Retrieving a map of all Webcasts for which a Student has a relation with
         * (progress)
         * Each Webcast title is the key of the map, to which the
         * progression amount beween 1-100 is the map value
         */

        /*
         * NOTE contrary to Module, here the are no Webcast instances, because their
         * title is always unique! (opdrachtbeschrijving)
         */
        Map<String, Integer> webcastProgressMap = this.logic.receiveWebcastProgressForStudent(student);

        // Dropdown with each individual Webcast title
        Label webcastDropdownLabel = new Label(webcastProgressMap.keySet().size() + " webcast(s) available:");
        ComboBox<String> webcastDropdown = new ComboBox<>();
        webcastDropdown.setValue("-");
        webcastDropdown.getItems().add("-");

        for (String webcastName : webcastProgressMap.keySet()) {
            webcastDropdown.getItems().add(webcastName);
        }

        // Progress visualization using a slide and indicators
        Slider sliderWebcast = new Slider();
        sliderWebcast.setMin(0);
        sliderWebcast.setMax(100);
        ProgressBar pbWebcast = new ProgressBar(0);
        ProgressIndicator piWebcast = new ProgressIndicator(0);
        Button updateWebcastProgressButton = new Button("Update progress");
        updateWebcastProgressButton.setVisible(false);
        Label updateWebcastProgressLabel = new Label("");
        updateWebcastProgressLabel.setTextFill(Color.GREEN);

        // Making the progression updatable by the user, by the slider
        sliderWebcast.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    updateWebcastProgressLabel.setText("");
                    pbWebcast.setProgress(newValue.doubleValue() / 100);
                    piWebcast.setProgress(newValue.doubleValue() / 100);
                });

        // Every time another webcast is selected, the progress indicators and sliders
        // get
        // the current value from the module
        webcastDropdown.setOnAction(webcastSelected -> {
            updateWebcastProgressLabel.setText("");
            String nameOfSelectedWebcast = webcastDropdown.getValue();
            if (nameOfSelectedWebcast.equals("-")) {
                return;
            }

            int progressionPercentage = this.logic.receiveWebcastProgressForStudent(student).get(nameOfSelectedWebcast);
            sliderWebcast.setValue(progressionPercentage);
            updateWebcastProgressButton.setVisible(true);
        });

        // Action event for the progress update button, so that progress of the selected
        // module gets updated based on the progress indicator (set by user)
        updateWebcastProgressButton.setOnMouseClicked(clicked -> {
            String nameOfSelectedWebcast = webcastDropdown.getValue();

            if (nameOfSelectedWebcast.isEmpty() || nameOfSelectedWebcast.equals("-")) {
                return;
            }

            int newProgressAmount = (int) Math.round(sliderWebcast.getValue());
            sliderWebcast.setValue(newProgressAmount);

            this.logic.updateProgressContentItem(nameOfSelectedWebcast, student, newProgressAmount);
            updateWebcastProgressLabel.setText("Updated!");

        });

        // Functionality to link another webcast to the student
        Label linkWebcastLabel = new Label("Add webcast");
        Button selectWebcastButton = new Button("Select webcast");
        selectWebcastButton.setId("EditBtn");
        ComboBox<String> dropdownOfLinkableWebcasts = new ComboBox<>();
        dropdownOfLinkableWebcasts.setVisible(false);

        // Setting the necessary components, outside the scope of the action events here
        // beneath
        Button linkButton = new Button("Link");
        linkButton.setVisible(false);
        Label confirmMSG = new Label("Linked!");
        confirmMSG.setTextFill(Color.GREEN);
        confirmMSG.setVisible(false);

        // Retrieving webcasts available for linking, adding them to the dropdown
        selectWebcastButton.setOnMouseClicked(clicked -> {
            dropdownOfLinkableWebcasts.getItems().clear();
            dropdownOfLinkableWebcasts.setVisible(true);
            linkButton.setVisible(false);
            confirmMSG.setVisible(false);

            List<String> namesOfWebcasts = logic.getWebcastsNotYetLinkedWithStudent(student);
            String dynamicDefaultValue = namesOfWebcasts.size() + " webcast(s) available";
            dropdownOfLinkableWebcasts.setValue(dynamicDefaultValue);
            namesOfWebcasts.forEach(name -> dropdownOfLinkableWebcasts.getItems().add(name));

            // If a webcast is selected from the dropdown, a link button will appear were a
            // user can triger the functionality of linking a webcast with a student.
            dropdownOfLinkableWebcasts.setOnMouseClicked(webcastSelected -> {
                String dropDownValue = dropdownOfLinkableWebcasts.getValue();
                if (dropDownValue.isEmpty() || dropDownValue.equals("-")) {
                    return;
                }

                linkButton.setVisible(true);

                linkButton.setOnMouseClicked(click -> {
                    if (dropdownOfLinkableWebcasts.getValue().isEmpty()) {
                        return;
                    }
                    String webcastName = dropdownOfLinkableWebcasts.getValue();
                    this.logic.studentStartsWatching(webcastName, student);

                    dropdownOfLinkableWebcasts.setVisible(false);
                    dropdownOfLinkableWebcasts.getItems().clear();
                    linkButton.setVisible(false);
                    confirmMSG.setVisible(true);

                    // For better user experience, directly updating the dropdown of linked
                    // webcasts, so that the user does not have to refresh the view
                    webcastDropdown.getItems().clear();
                    this.logic.receiveWebcastProgressForStudent(student).keySet()
                            .forEach(name -> webcastDropdown.getItems().add(name));
                    webcastDropdown.setValue("-");
                    webcastDropdown.getItems().add("-");
                    webcastDropdownLabel
                            .setText(this.logic.receiveWebcastProgressForStudent(student).keySet().size()
                                    + " webcast(s) available:");

                });
            });
        });

        // Setting up the view with the elements to link a webcast with a user
        view.add(linkWebcastLabel, 0, 7);
        view.add(selectWebcastButton, 0, 8);
        view.add(dropdownOfLinkableWebcasts, 0, 9);
        view.add(linkButton, 0, 10);
        view.add(confirmMSG, 0, 11);

        // Setting up the view with the elements for Webcast progress, and showing
        // (activating) the view to the user
        view.add(webcastDropdownLabel, 1, 7);
        view.add(webcastDropdown, 1, 8);
        view.add(sliderWebcast, 1, 9);
        view.add(pbWebcast, 1, 10);
        view.add(piWebcast, 1, 11);
        view.add(updateWebcastProgressButton, 2, 10);
        view.add(updateWebcastProgressLabel, 2, 12);
        activate(view, "Content progress of Student: " + student.getStudentName());

    }

    private void addStudentView() {
        GridPane view = generateFormGrid();
        Label title = new Label("Create new student");
        title.setId("title");
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
        ComboBox<Gender> genderBox = new ComboBox<>();
        genderBox.setId("selectBox");
        final Gender defaultChoice = Gender.O;
        genderBox.getItems().addAll(Gender.F, Gender.M, Gender.O);
        genderBox.setValue(defaultChoice);

        // These three input fields make up the date of birth
        TextField dayField = new TextField();
        TextField monthField = new TextField();
        TextField yearField = new TextField();

        // Together with the right label, are the input fields for day, month and year
        // stored in a VBOX;
        VBox days = new VBox(new Label("Day"), dayField);
        VBox month = new VBox(new Label("Month"), monthField);
        VBox year = new VBox(new Label("Year"), yearField);
        // These three VBoxes are put in a HBox;
        HBox birthdayBox = new HBox(days, month, year);
        birthdayBox.setSpacing(25);

        // Street, Postalcode and Housenumber make up the address
        TextField street = new TextField();
        TextField postalCode = new TextField();
        TextField houseNr = new TextField();

        // The three textfields are put in these three Vboxes
        VBox streetBox = new VBox(new Label("Street"), street);
        VBox houseNumber = new VBox((new Label("House number")), houseNr);
        VBox postalBox = new VBox(new Label("Postalcode"), postalCode);
        // These VBOXES are put in a HBOX
        HBox addressInformation = new HBox(streetBox, houseNumber, postalBox);
        addressInformation.setSpacing(25);

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
        view.add(addressInformation, 1, 5);
        view.add(countryField, 1, 6);
        view.add(cityField, 1, 7);
        view.add(warningDateOfBirth, 2, 4);
        view.add(warningEmail, 2, 3);
        // Button to submit and go to successful screen
        Button studentCreationButton = new Button("Create student");
        view.add(studentCreationButton, 1, 9);
        view.add(lastWarningText, 2, 9);

        // Eventhandler

        studentCreationButton.setOnMouseClicked(clicked -> {
            // It calls the method datOfBirthIsValid in order if the date of birth is valid
            // It checks if the date is not further than now
            // It also checks if the date of birth textFields are filled.
            boolean dateValid = InputValidation.dateOfBirthIsValid(dayField.getText(), monthField.getText(),
                    yearField.getText());

            // Method addressIsValid combines multiple methods (FieldIsnotEmpty() for
            // houseNr, Street and postal fields), Postalcode has the correct format
            // And checks if the houseNr is an digit
            boolean addresValid = InputValidation.addressIsValid(street.getText(), houseNr.getText(),
                    postalCode.getText());
            // Checks if input fields are not empty
            boolean nameIsFilled = InputValidation.fieldIsNotEmpty(nameField.getText());
            boolean emailIsValid = InputValidation.validateMailAddress(emailField.getText());
            boolean cityIsValid = InputValidation.fieldIsNotEmpty(cityField.getText());
            boolean countryIsFilled = InputValidation.fieldIsNotEmpty(countryField.getText());
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
            // separately
            if (nameIsFilled && emailIsValid && cityIsValid && countryIsFilled && dateValid && addresValid) {
                String name = nameField.getText();
                String email = emailField.getText();
                Gender gender = genderBox.getValue();
                LocalDate dateOfBirth = InputValidation.formatDate(yearField.getText(), monthField.getText(),
                        dayField.getText());
                String streetName = street.getText();
                int hNumber = Integer.parseInt(houseNr.getText());
                String postCode = InputValidation.formatPostalCode(postalCode.getText());
                String countryName = countryField.getText();
                String cityName = cityField.getText();
                this.logic.newStudent(name, email, dateOfBirth, gender, streetName, hNumber, postCode, countryName,
                        cityName);
                // When successfully inserted, it will go to the succesfullprocesView
                successfullyProcessView();
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
        welcomeToFormLabel.setId("title");
        welcomeToFormLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeToFormLabel, 1, 0, 2, 1);
        // Gender combobox
        ComboBox<Gender> genderBox = new ComboBox<>();
        genderBox.setId("selectBox");
        genderBox.setValue(studentToEdit.getGender());
        genderBox.getItems().addAll(Gender.F, Gender.M, Gender.O);
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

        // Vertical boxes for address information(street, housenumber, postalcode)
        VBox street = new VBox((new Label("Street")), streetField);
        VBox house = new VBox((new Label("Housenumber")), houseNumber);
        VBox postal = new VBox((new Label("Postalcode")), postalCodeField);
        // Horizontal box to put all the address information together
        HBox address = new HBox(street, house, postal);
        address.setSpacing(25);
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
        view.add(address, 1, 4);
        view.add(countryField, 1, 5);
        view.add(cityField, 1, 6);
        // Button to submit changes
        Button updateButton = new Button("Update student");
        view.add(updateButton, 1, 7);

        Text lastWarningText = new Text("");
        view.add(lastWarningText, 2, 7);
        updateButton.setOnMouseClicked(clicked -> {
            // Validate input fields date of birth and address
            // It also checks if the date of birth or address fields are blank
            boolean dateValid = InputValidation.dateOfBirthIsValid(dayField.getText(), monthField.getText(),
                    yearField.getText());
            // Assigns true to addressValid value if all the conditions regarding address
            // are met
            boolean addressValid = InputValidation.addressIsValid(streetField.getText(), houseNumber.getText(),
                    postalCodeField.getText());
            // Checks if input fields are not empty
            boolean nameIsFilled = InputValidation.fieldIsNotEmpty(nameField.getText());
            boolean cityIsValid = InputValidation.fieldIsNotEmpty(cityField.getText());
            boolean countryIsFilled = InputValidation.fieldIsNotEmpty(countryField.getText());
            // Checks if the boolean values are true
            if (nameIsFilled && cityIsValid && countryIsFilled && dateValid && addressValid) {
                // Converts input to String, LocalDate or Gender
                String name = nameField.getText();
                Gender gender = genderBox.getValue();
                LocalDate dateOfBirth = InputValidation.formatDate(yearField.getText(), monthField.getText(),
                        dayField.getText());
                String streetName = streetField.getText();
                int hNumber = Integer.parseInt(houseNumber.getText());
                String postCode = InputValidation.formatPostalCode(postalCodeField.getText());
                String countryName = countryField.getText();
                String cityName = cityField.getText();
                // Passes attributes to the logic class
                this.logic.updateStudent(studentToEdit, name, dateOfBirth, gender, streetName, cityName, countryName,
                        hNumber, postCode);

                successfullyProcessView();
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

        ComboBox<String> boxes = new ComboBox<>();
        boxes.setId("selectBox");
        final String defaultValue = "Select course";
        boxes.setValue(defaultValue);
        List<String> courseList = this.enrollmentLogic.getCourseNames();
        boxes.getItems().addAll(courseList);

        Button submitEnrollmentButton = new Button("Enroll student");
        view.add(title, 0, 0);
        view.add(boxes, 0, 1);
        view.add(submitEnrollmentButton, 0, 2);

        submitEnrollmentButton.setOnMouseClicked(clicked -> {
            if (boxes.getValue().equals(defaultValue)) {
                warning.setText("Choose course");
                return;
            }
            Student student = this.logic.getStudentByEmail(studentEmail);
            this.enrollmentLogic.enrollStudentToCourse(student, boxes.getValue().toString());
            successfullyProcessView();
        });

        activate(view, "Enroll student");
    }

    private void successfullyProcessView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully processed");
        Button homeBtn = new Button("Home");
        Button resumeBtn = new Button("Resume");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(resumeBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        resumeBtn.setOnMouseClicked(clicked -> new StudentManagementView(this.gui).createView());
        activate(view, "Successful process");
    }

}
