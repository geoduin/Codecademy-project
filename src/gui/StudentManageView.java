package gui;

import java.time.LocalDate;

import domain.Student;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        studentList.getItems().add(defaultStudentValue);
        // Retrieves the list of studentnames from database, with a hashMap<name,
        // emailaddres>

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
            // Pick student from database, dependant on the chosen studentname
            // editStudentView(studentToEdit);
        });

        deleteStudent.setOnMouseClicked(clicked -> {
            // Dependant on the chosen studentname and email addres
            // It will delete that student

        });
        // See abstract class "View"
        activate(view, "Student management");

    }

    public void addStudentView() {
        GridPane view = generateFormGrid();
        Label title = new Label("Create new student");
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

        // Togglegroup holds the values of M, F, O together
        ComboBox genderBox = new ComboBox<>();
        final String defaultChoice = "O";
        genderBox.getItems().addAll("O", "M", "F");
        genderBox.setValue(defaultChoice);

        TextField dayField = new TextField();
        TextField monthField = new TextField();
        TextField yearField = new TextField();

        VBox days = new VBox(new Label("Day"), dayField);
        VBox month = new VBox(new Label("Month"), monthField);
        VBox year = new VBox(new Label("Year"), yearField);
        HBox birthdayBox = new HBox(days, month, year);
        birthdayBox.setSpacing(25);

        TextField streetPlusNumber = new TextField();
        TextField postalCode = new TextField();
        VBox streetBox = new VBox(new Label("Street + Housenumber"), streetPlusNumber);
        VBox postalBox = new VBox(new Label("Postalcode"), postalCode);
        HBox addresInformation = new HBox(streetBox, postalBox);
        addresInformation.setSpacing(25);

        // Warning labels
        Text warningDateOfBirth = new Text("");
        Text warningAddres = new Text("");
        Text lastWarningText = new Text("");

        // Layout first column
        view.add(title, 1, 0);
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
        view.add(warningAddres, 2, 5);
        // Button to submit and go to succesfull screen
        Button studentCreationButton = new Button("Create student");
        view.add(studentCreationButton, 1, 9);
        view.add(lastWarningText, 2, 9);

        // Eventhandler
        emailField.textProperty().addListener((change, oldvalue, newValue) -> {

            // Checks emailformatting
            // If invalid it warning email setText to "Invalid email"
            // Boolean set false

        });

        // Postal code eventhandler
        postalCode.textProperty().addListener((change, old, newValue) -> {
            // Checks postalformatting
        });

        studentCreationButton.setOnMouseClicked(clicked -> {
            // Validate inputfields

            // Checks if inputfields are not empty

            // Last check

            // Send to database

            // Goes to succesfull screen
        });
        activate(view, "Add student");

    }

    public void editStudentView(Student studentToEdit) {
        GridPane view = generateFormGrid();
        // In case we want to edit his email
        String originalEmail = studentToEdit.getEmail();

        Label title = new Label("Create new student");
        // Inputfields
        ComboBox genderBox = new ComboBox<>();
        genderBox.setValue(studentToEdit.getGender().toString());
        genderBox.getItems().addAll("O", "F", "M");
        TextField nameField = new TextField(studentToEdit.getStudentName());
        TextField emailField = new TextField(studentToEdit.getEmail());

        // input fields filled with information of the student
        TextField dayField = new TextField((Integer.toString(studentToEdit.getDateOfBirth().getDayOfMonth())));
        TextField monthField = new TextField((Integer.toString(studentToEdit.getDateOfBirth().getMonthValue())));
        TextField yearField = new TextField((Integer.toString(studentToEdit.getDateOfBirth().getYear())));
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

        // Layout first column
        view.add(new Label("Name"), 0, 1);
        view.add(new Label("GenderBox"), 0, 2);
        view.add(new Label("Emailadress"), 0, 3);
        view.add(new Label("Date of birth"), 0, 4);
        view.add(new Label("Addres"), 0, 5);
        view.add(new Label("Country"), 0, 6);
        view.add(new Label("City"), 0, 7);
        // Layout second column
        view.add(nameField, 1, 1);
        view.add(genderBox, 1, 2);
        view.add(emailField, 1, 3);
        view.add(birthdayBox, 1, 4);
        view.add(addres, 1, 5);
        view.add(countryField, 1, 6);
        view.add(cityField, 1, 7);
        // Button to submit changes
        Button updateButton = new Button("Update student");

        updateButton.setOnMouseClicked(clicked -> {
            // Validates any input values

            // Checks if inputfields are not empty

            // Checks if both conditions are met

            // Updates student information
        });
        activate(view, "Edit student information");
    }

    public void readAllStudentsView() {
        GridPane view = generateGrid();

        activate(view, "All students");
    }

}
