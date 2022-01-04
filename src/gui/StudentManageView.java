package gui;

import java.util.Map;
import domain.Student;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.StudentLogic;

class StudentManageView extends View {
    private StudentLogic studentLogic;

    StudentManageView(GUI gui) {
        super(gui);
        // TODO Auto-generated constructor stub
        this.studentLogic = new StudentLogic();
    }

    @Override
    protected void createView() {

        GridPane view = generateGrid();

        // First column
        Label editStudentLabel = new Label("Select student:");
        ComboBox studentList = new ComboBox<>();

        final String defaultStudentValue = "Select student";
        studentList.setValue(defaultStudentValue);
        studentList.getItems().add(defaultStudentValue);
        // Retrieves the list of studentnames from database, with a hashMap<name,
        // emailaddres>
        Map<String, String> studentMap = this.studentLogic.getNameAndEmail();
        for (String name : studentMap.keySet()) {
            studentList.getItems().add(name);
        }

        // Based on the value of the combobox value, it will either delete or edit the
        // student information.
        Button editStudentButton = new Button("Edit student");
        Button deleteStudent = new Button("Delete student");
        Button readAllStudent = new Button("Show all students");

        // Second column
        Label createStudentLabel = new Label("Create student:");
        // The plus sign will lead you to the create new student view.
        Button createStudentButton = new Button("+");
        createStudentButton.setOnMouseClicked(clicked -> addStudentView());
        // Layout of the manage view
        view.add(editStudentLabel, 0, 0);
        view.add(studentList, 0, 1);
        view.add(editStudentButton, 0, 2);
        view.add(deleteStudent, 0, 3);
        view.add(readAllStudent, 0, 4);
        view.add(createStudentLabel, 1, 0);
        view.add(createStudentButton, 1, 1);

        // Button to edit
        editStudentButton.setOnMouseClicked(clicked -> {
            // It takes the value of the chosen key from the combobox
            // This value is the email.
            // Sends it to the logic class and picks the student from the database
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label("Not selected anything"), 0, 5);
                return;
            }
            Student pickedStudent = this.studentLogic.getStudentByEmail(studentMap.get(studentList.getValue()));
            editStudentView(pickedStudent);
        });

        deleteStudent.setOnMouseClicked(clicked -> {
            // Dependant on the chosen studentname and email addres
            // It will delete that student
            if (studentList.getValue().equals(defaultStudentValue)) {
                view.add(new Label("Not selected anything"), 0, 5);
                return;
            }
            String chosenStudentEmail = studentMap.get(studentList.getValue());
            this.studentLogic.deleteStudentByEmail(chosenStudentEmail);
            succesfullProcesView();
        });
        // See abstract class "View"
        activate(view, "Student management");

    }

    private void addStudentView() {
        GridPane view = generateFormGrid();
        Label title = new Label("Create new student");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        // Labels
        Label nameLabel = new Label("Name");
        Label genderBoxLabel = new Label("GenderBox");
        Label emailLabel = new Label("Emailadress");
        Label dateOfBirthLabel = new Label("Date of birth");
        Label addresLabel = new Label("Addres");
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
        VBox houseNumber = new VBox((new Label("Housenumber")), houseNr);
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
        view.add(addresLabel, 0, 5);
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
            boolean dateValid = studentLogic.dateOfBirthIsValid(dayField.getText(), monthField.getText(),
                    yearField.getText());
            boolean addresValid = studentLogic.addresIsValid(street.getText(), houseNr.getText(), postalCode.getText());
            // Checks if inputfields are not empty
            boolean nameIsFilled = studentLogic.fieldIsNotEmpty(nameField.getText());
            boolean emailIsValid = studentLogic.validateMailAddress(emailField.getText());
            boolean cityIsValid = studentLogic.fieldIsNotEmpty(cityField.getText());
            boolean countryIsFilled = studentLogic.fieldIsNotEmpty(countryField.getText());
            // if the date is not valid, than it will show a warning
            if (!(dateValid)) {
                warningDateOfBirth.setText("Date of birth is invalid");
            }

            // If email already exist, than it shows a warning
            if (studentLogic.emailExist(emailField.getText())) {
                warningEmail.setText("Email already exist!");
                return;
            }

            // If every boolean value is true, than it will insert the new student to the
            // studentLogic to create a new student and will be send to the database
            // NOTE: When tested, I changed my database in order put housenumber and street
            // seperately
            if (nameIsFilled && emailIsValid && cityIsValid && countryIsFilled && dateValid && addresValid) {
                this.studentLogic.newStudent(nameField.getText(), emailField.getText(),
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
        view.add(new Label("GenderBox"), 0, 2);
        view.add(new Label("Date of birth"), 0, 3);
        view.add(new Label("Addres"), 0, 4);
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
            boolean dateValid = studentLogic.dateOfBirthIsValid(dayField.getText(), monthField.getText(),
                    yearField.getText());
            boolean addresValid = studentLogic.addresIsValid(streetField.getText(), houseNumber.getText(),
                    postalCodeField.getText());
            // Checks if inputfields are not empty
            boolean nameIsFilled = studentLogic.fieldIsNotEmpty(nameField.getText());
            boolean cityIsValid = studentLogic.fieldIsNotEmpty(cityField.getText());
            boolean countryIsFilled = studentLogic.fieldIsNotEmpty(countryField.getText());
            // Checks if the boolean values are true
            if (nameIsFilled && cityIsValid && countryIsFilled && dateValid && addresValid) {
                System.out.println("Succesfull");
                this.studentLogic.updateStudent(studentToEdit, nameField.getText(),
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

    private void succesfullProcesView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully processed");
        Button homeBtn = new Button("Home");
        Button resumeBtn = new Button("Resume");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(resumeBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        resumeBtn.setOnMouseClicked(clicked -> new StudentManageView(this.gui).createView());
        activate(view, "Successful proces");
    }

}
