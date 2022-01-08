package gui;

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
import logic.ModuleLogic;

import java.util.HashMap;

import domain.Module;
import domain.Status;

//Class responsible for all the views that are related to the management of modules
class ModuleManagementView extends View {
    private ModuleLogic logic;

    ModuleManagementView(GUI baseUI) {
        super(baseUI);
        this.logic = new ModuleLogic();
    }

    // The following method creates the first view that the user sees when going
    // into module management.
    @Override
    protected void createView() {
        // Initial layout setup
        GridPane view = generateGrid();

        // UI components
        Label createLabel = new Label("Create a module");
        Button addModuleBtn = new Button("+");
        addModuleBtn.setOnMouseClicked(clicked -> addModuleView());

        // Dropdown showing existing modules
        Label selectLabel = new Label("Select an existing module");
        ComboBox<String> dropdown = new ComboBox<>();
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        // porting the modules to the dropdown
        final String defaultDropdownValue = "-no module selected-";
        dropdown.setValue(defaultDropdownValue);
        dropdown.getItems().add(defaultDropdownValue);
        HashMap<String, Integer> moduleNameVersionAndIDPairs = this.logic.getModuleNamesVersionsAndIDs();
        for (String moduleNameAndVersion : moduleNameVersionAndIDPairs.keySet()) {
            dropdown.getItems().add(moduleNameAndVersion);
        }

        // Edit button action, retrieving the selected module to edit
        editBtn.setOnMouseClicked(clicked -> {
            if (dropdown.getValue().equals(defaultDropdownValue)) {
                view.add(new Label("No module selected!"), 0, 4);
            } else {
                // getting the to-be-edited module
                Module moduleToEdit = this.logic
                        .retrieveModuleByID((moduleNameVersionAndIDPairs.get(dropdown.getValue())));
                editModuleView(moduleToEdit);
            }
        });

        // Delete button action, retrieving the selected module to delete
        deleteBtn.setOnMouseClicked(clicked -> {
            if (dropdown.getValue().equals(defaultDropdownValue)) {
                view.add(new Label("No module selected!"), 0, 4);
            } else {
                // called method deletes module + sends a true if succesfull
                if (this.logic.deleteModule(moduleNameVersionAndIDPairs.get(dropdown.getValue()))) {
                    successfullyDeletedView();
                }
            }
        });

        // futher layout setup
        view.add(createLabel, 1, 0);
        view.add(addModuleBtn, 1, 1);
        view.add(selectLabel, 0, 0);
        view.add(dropdown, 0, 1);
        view.add(editBtn, 0, 2);
        view.add(deleteBtn, 0, 3);

        activate(view, "Module management");

    }

    // Creating a view for the user to create a new module
    private void addModuleView() {
        // Initial layout setup
        GridPane view = generateFormGrid();

        // form label
        Label welcomeToFormLabel = new Label("Create a module");
        welcomeToFormLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeToFormLabel, 0, 0, 2, 1);
        GridPane.setHalignment(welcomeToFormLabel, HPos.LEFT);
        GridPane.setMargin(welcomeToFormLabel, new Insets(20, 0, 20, 0));

        // Module name input
        Label moduleNameLabel = new Label("Titel:");
        TextField moduleNameField = new TextField();
        Text moduleNameInputErrorText = new Text("");
        moduleNameInputErrorText.setFill(Color.FIREBRICK);
        view.add(moduleNameLabel, 0, 1);
        view.add(moduleNameField, 1, 1);
        view.add(moduleNameInputErrorText, 2, 1);
        GridPane.setHalignment(moduleNameInputErrorText, HPos.RIGHT);

        // Version input
        Label versionLabel = new Label("Version:");
        TextField versionField = new TextField();
        Text versionInputError = new Text("");
        final String positiveNumberErrorMSG = "has to be a (positive) rounded number!";
        versionInputError.setFill(Color.FIREBRICK);
        view.add(versionLabel, 0, 2);
        view.add(versionField, 1, 2);
        view.add(versionInputError, 2, 2);

        // Checking (live) if the user doesn't put anything in other than an int
        versionField.textProperty().addListener((change, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") && !(newValue.equals(""))) {
                versionInputError.setText(positiveNumberErrorMSG);
            } else {
                versionInputError.setText("");
            }
        });

        // Order number input
        Label orderNumberLabel = new Label("Order it will get within a course:");
        TextField orderNumberField = new TextField();
        Text orderNumberInputError = new Text("");
        orderNumberInputError.setFill(Color.FIREBRICK);
        view.add(orderNumberLabel, 0, 3);
        view.add(orderNumberField, 1, 3);
        view.add(orderNumberInputError, 2, 3);

        // Checking (live) if the user doesn't put anything in other than an int
        orderNumberField.textProperty().addListener((change, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") && !(newValue.equals(""))) {
                orderNumberInputError.setText(positiveNumberErrorMSG);
            } else {
                orderNumberInputError.setText("");
            }
        });

        // Description input
        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();
        view.add(descriptionLabel, 0, 4);
        view.add(descriptionField, 1, 4);

        // Status input
        Label statusLabel = new Label("Module status:");
        ComboBox<String> dropdown = new ComboBox<>();
        Text noStatusSelectedError = new Text("");
        noStatusSelectedError.setFill(Color.FIREBRICK);
        final String defaultDropDownString = "-please select a value-";
        dropdown.setValue(defaultDropDownString);
        dropdown.getItems().add(defaultDropDownString);
        dropdown.getItems().add("ACTIVE");
        dropdown.getItems().add("CONCEPT");
        dropdown.getItems().add("ARCHIVED");
        view.add(statusLabel, 0, 5);
        view.add(dropdown, 1, 5);
        view.add(noStatusSelectedError, 2, 5);

        // Contact input
        Label contactLabel = new Label("Name of contactperson:");
        TextField contactField = new TextField();
        view.add(contactLabel, 0, 6);
        view.add(contactField, 1, 6);

        Label contactEmailLabel = new Label("Email of contactperson:");
        TextField contactEmailField = new TextField();
        view.add(contactEmailLabel, 0, 7);
        view.add(contactEmailField, 1, 7);

        // Create module button
        Button createButton = new Button("Create module");
        createButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        view.add(createButton, 1, 8);

        // Error to show when not all fields have an input
        Text nullOrAlreadyExistsErrorField = new Text("");
        nullOrAlreadyExistsErrorField.setFill(Color.FIREBRICK);
        view.add(nullOrAlreadyExistsErrorField, 1, 9);

        // Input validation when user presses 'create'
        createButton.setOnMouseClicked(clicked -> {
            // check if any field has no input
            if (hasNoInput(moduleNameField) || hasNoInput(descriptionField) || hasNoInput(contactField)
                    || hasNoInput(contactEmailField)) {
                nullOrAlreadyExistsErrorField.setText("One or more fields are not filled!");
                return;
            } else {
                nullOrAlreadyExistsErrorField.setText("");
            }

            // checking if dropdown has no selected value
            if (dropdown.getValue().equals(defaultDropDownString)) {
                noStatusSelectedError.setText("No value selected!");
                return;
            } else {
                noStatusSelectedError.setText("");
            }

            // Checking if version- and orderfield have an integer-type input
            String versionInput = versionField.getText();
            if (!versionInput.matches("\\d+") || versionInput.equals("")) {
                versionInputError.setText(positiveNumberErrorMSG);
                return;
            } else {
                versionInputError.setText("");
            }

            String orderNumberInput = orderNumberField.getText();
            if (!orderNumberInput.matches("\\d+") || orderNumberInput.equals("")) {
                orderNumberInputError.setText(positiveNumberErrorMSG);
                return;

            } else {
                orderNumberInputError.setText("");
            }

            // If the whole input validation is done, the module gets created if it doesn't
            // exist yet.
            String title = moduleNameField.getText();
            int version = Integer.parseInt(versionField.getText());
            if (this.logic.moduleAlreadyExistsBasedOn(title, version)) {
                nullOrAlreadyExistsErrorField.setText("Module already exists!");
                return;
            }

            // getting the correct enumerator
            // todo CONTROLEREN OF DIT EFFICIËNTER KAN!
            Status status;
            if (dropdown.getValue().equals("ACTIVE")) {
                status = domain.Status.ACTIVE;
            } else if (dropdown.getValue().equals("ARCHIVED")) {
                status = domain.Status.ARCHIVED;
            } else {
                status = domain.Status.CONCEPT;
            }

            // Creating the module
            this.logic.newModule(status, title, version,
                    Integer.valueOf(orderNumberField.getText()), descriptionField.getText(),
                    contactField.getText(), contactEmailField.getText(), 0);
            moduleSuccessfullyCreatedView();
        });
        activate(view, "Create module");
    }

    // Creating a view for the user to edit a module
    private void editModuleView(Module moduleToEdit) {
        GridPane view = generateFormGrid();

        // form label
        Label welcomeToFormLabel = new Label("Editing: '" + moduleToEdit.getTitle() + "'");
        welcomeToFormLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeToFormLabel, 0, 0, 2, 1);
        GridPane.setHalignment(welcomeToFormLabel, HPos.LEFT);
        GridPane.setMargin(welcomeToFormLabel, new Insets(20, 0, 20, 0));

        // Order number input
        Label orderNumberLabel = new Label("Order it has within a course:");
        TextField orderNumberField = new TextField("" + moduleToEdit.getPositionWithinCourse());
        Text orderNumberInputError = new Text("");
        orderNumberInputError.setFill(Color.FIREBRICK);
        view.add(orderNumberLabel, 0, 1);
        view.add(orderNumberField, 1, 1);
        view.add(orderNumberInputError, 2, 1);

        // Checking (live) if the user doesn't put anything in other than an int
        orderNumberField.textProperty().addListener((change, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") && !(newValue.equals(""))) {
                orderNumberInputError.setText("Has to be a (positive) rounded number");
            } else {
                orderNumberInputError.setText("");
            }
        });

        // Contactperson name input
        Label contactLabel = new Label("Name of contactperson:");
        TextField contactField = new TextField(moduleToEdit.getContactName());
        view.add(contactLabel, 0, 2);
        view.add(contactField, 1, 2);

        // Contactperson email input
        Label contactEmailLabel = new Label("Email of contactperson:");
        TextField contactEmailField = new TextField(moduleToEdit.getEmailAddress());
        view.add(contactEmailLabel, 0, 3);
        view.add(contactEmailField, 1, 3);

        // Status
        Label statusLabel = new Label("Module status:");
        ComboBox<String> dropdown = new ComboBox<>();
        Text noStatusSelectedError = new Text("");
        noStatusSelectedError.setFill(Color.FIREBRICK);
        final String defaultDropDownString = "-please select a value-";
        dropdown.setValue(moduleToEdit.getStatus().toString());
        dropdown.getItems().add(defaultDropDownString);
        dropdown.getItems().add("ACTIVE");
        dropdown.getItems().add("CONCEPT");
        dropdown.getItems().add("ARCHIVED");
        view.add(statusLabel, 0, 5);
        view.add(dropdown, 1, 5);
        view.add(noStatusSelectedError, 2, 5);

        // Description
        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField(moduleToEdit.getDescription());
        view.add(descriptionLabel, 0, 4);
        view.add(descriptionField, 1, 4);

        Button editBtn = new Button("Edit module");
        editBtn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        view.add(editBtn, 0, 6);
        // Edit button

        Text nullOrAlreadyExistsErrorField = new Text("");
        nullOrAlreadyExistsErrorField.setFill(Color.FIREBRICK);
        view.add(nullOrAlreadyExistsErrorField, 1, 9);

        editBtn.setOnMouseClicked((clicked -> {
            if (dropdown.getValue().equals(defaultDropDownString)) {
                noStatusSelectedError.setText("No value selected!");
                return;
            } else {
                noStatusSelectedError.setText("");
            }

            if (hasNoInput(descriptionField) || hasNoInput(contactField)
                    || hasNoInput(contactEmailField)) {
                nullOrAlreadyExistsErrorField.setText("One or more fields are not filled!");
                return;
            } else {
                nullOrAlreadyExistsErrorField.setText("");
            }

            this.logic.editModule(contactEmailField.getText(), contactField.getText(), descriptionField.getText(),
                    dropdown.getValue(), Integer.parseInt(orderNumberField.getText()), moduleToEdit);

            moduleSuccessfullyEditedView();

        }));
        activate(view, "Edit module");
    }

    // When a module is edited, this method creates a view to give the user a
    // general menu
    private void moduleSuccessfullyEditedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully Edited!");
        Button homeBtn = new Button("Go home");
        Button editAnotherModuleBtn = new Button("Edit other");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(editAnotherModuleBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        editAnotherModuleBtn.setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).createView());
        activate(view, "Successfully edited!");
    }

    // If a module can be created, a user has an easy option to directly add
    // another
    private void moduleSuccessfullyCreatedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully created!");
        Button homeBtn = new Button("Go home");
        Button createAnotherModuleBtn = new Button("Create other");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(createAnotherModuleBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        createAnotherModuleBtn
                .setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).addModuleView());
        activate(view, "Successfully created!");
    }

    // When a module is deleted, this method creates a view to give the user a
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
        backBtn.setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).createView());
        activate(view, "Successfully deleted");
    }
}