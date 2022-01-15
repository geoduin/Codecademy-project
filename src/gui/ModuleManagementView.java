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
import logic.InputValidation;
import logic.ModuleLogic;

import java.util.HashMap;
import java.util.Map;

import domain.Module;
import domain.Status;

//Class responsible for all the views that are related to the management of modules (add, edit, delete modules)
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
        addModuleBtn.setId("addButton");
        addModuleBtn.setOnMouseClicked(clicked -> addModuleView());

        // Dropdown and labels showing existing modules en giving action options to the
        // user
        Label selectLabel = new Label("Select an existing module");
        Label noModuleSelectedLabel = new Label("No module selected!");
        noModuleSelectedLabel.setVisible(false);

        ComboBox<String> dropdown = new ComboBox<>();

        Button editBtn = new Button("Edit");
        editBtn.setId("editButton");
        Button deleteBtn = new Button("Delete");
        deleteBtn.setId("deleteButton");
        Label deleteFailedLabel = new Label("Linked with a course, unlink first before deletion.");
        deleteFailedLabel.setVisible(false);

        // porting the modules to the dropdown
        int count = 0;
        Map<String, Integer> moduleNameVersionAndIDPairs = this.logic.getModuleNamesVersionsAndIDs();
        for (String moduleNameAndVersion : moduleNameVersionAndIDPairs.keySet()) {
            dropdown.getItems().add(moduleNameAndVersion);
            count++;
        }
        dropdown.setPromptText(count + " module(s) available");

        // Edit button action, retrieving the selected module to edit
        editBtn.setOnMouseClicked(clicked -> {
            if (dropdown.getValue() == null) {
                noModuleSelectedLabel.setVisible(true);
            } else {
                noModuleSelectedLabel.setVisible(false);

                // getting the to-be-edited module
                Module moduleToEdit = this.logic
                        .retrieveModuleByID((moduleNameVersionAndIDPairs.get(dropdown.getValue())));
                editModuleView(moduleToEdit);
            }
        });

        // Delete button action, retrieving the selected module to delete
        deleteBtn.setOnMouseClicked(clicked -> {
            if (dropdown.getValue() == null) {
                noModuleSelectedLabel.setVisible(true);
            } else {
                noModuleSelectedLabel.setVisible(false);

                // called method deletes module + sends a true if succesfull
                if (this.logic.deleteModule(moduleNameVersionAndIDPairs.get(dropdown.getValue()))) {
                    successfullyDeletedView();
                } else {
                    deleteFailedLabel.setVisible(true);
                }
            }
        });

        // futher layout setup and activation
        view.add(createLabel, 1, 0);
        view.add(addModuleBtn, 1, 1);
        view.add(selectLabel, 0, 0);
        view.add(dropdown, 0, 1);
        view.add(editBtn, 0, 2);
        view.add(deleteBtn, 0, 3);
        view.add(noModuleSelectedLabel, 0, 4);
        view.add(deleteFailedLabel, 0, 5);

        this.gui.goToNext(view, "Module management");

    }

    // Creating a view for the user to create a new module
    private void addModuleView() {
        // Initial layout setup
        GridPane view = generateFormGrid();

        // form label
        Label welcomeToFormLabel = new Label("Create a module");
        welcomeToFormLabel.setId("title");
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
        final String positiveNumberErrorMSG = "has to be a (positive) whole number!";
        versionInputError.setFill(Color.FIREBRICK);
        view.add(versionLabel, 0, 2);
        view.add(versionField, 1, 2);
        view.add(versionInputError, 2, 2);

        // Checking (live) if the user doesn't put anything in other than an int
        versionField.textProperty().addListener((change, oldValue, newValue) -> {
            if (!InputValidation.areNumbers(newValue) && InputValidation.fieldIsNotEmpty(newValue)) {
                versionInputError.setText(positiveNumberErrorMSG);
            } else {
                versionInputError.setText("");
            }
        });

        // Order number input
        Label orderNumberLabel = new Label("Position in course:");
        TextField orderNumberField = new TextField();
        Text orderNumberInputError = new Text("");
        orderNumberInputError.setFill(Color.FIREBRICK);
        view.add(orderNumberLabel, 0, 3);
        view.add(orderNumberField, 1, 3);
        view.add(orderNumberInputError, 2, 3);

        // Checking (live) if the user doesn't put anything in other than an int
        orderNumberField.textProperty().addListener((change, oldValue, newValue) -> {
            if (!InputValidation.areNumbers(newValue) && InputValidation.fieldIsNotEmpty(newValue)) {
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

        // Status input, via a dropdown
        Label statusLabel = new Label("Module status:");
        ComboBox<Status> dropdown = new ComboBox<>();
        dropdown.setPromptText("Select status");
        dropdown.setId("selectBox");
        Text noStatusSelectedError = new Text("No value selected!");
        noStatusSelectedError.setVisible(false);
        noStatusSelectedError.setFill(Color.FIREBRICK);
        dropdown.getItems().add(Status.ACTIVE);
        dropdown.getItems().add(Status.CONCEPT);
        dropdown.getItems().add(Status.ARCHIVED);
        view.add(statusLabel, 0, 5);
        view.add(dropdown, 1, 5);
        view.add(noStatusSelectedError, 2, 5);

        // Contact input
        Label contactLabel = new Label("Name of contact:");
        TextField contactField = new TextField();
        view.add(contactLabel, 0, 6);
        view.add(contactField, 1, 6);

        Label contactEmailLabel = new Label("Email of contact:");
        TextField contactEmailField = new TextField();
        Label invalidEmailErrorLabel = new Label("Invalid email format!");
        invalidEmailErrorLabel.setVisible(false);
        view.add(contactEmailLabel, 0, 7);
        view.add(contactEmailField, 1, 7);
        view.add(invalidEmailErrorLabel, 2, 7);

        // Create module button
        Button createButton = new Button("Create module");
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

            // Checks if email format is correct
            if (!(InputValidation.validateMailAddress(contactEmailField.getText()))) {
                invalidEmailErrorLabel.setVisible(true);
                return;
            } else {
                invalidEmailErrorLabel.setVisible(false);

            }

            // Checking if no status is selected from the dropdown
            if (dropdown.getValue() == null) {
                noStatusSelectedError.setVisible(true);
                return;
            } else {
                noStatusSelectedError.setVisible(false);

            }

            // Checking if version- and orderfield have an integer-type input
            String versionInput = versionField.getText();
            if (!InputValidation.areNumbers(versionInput) || !InputValidation.fieldIsNotEmpty(versionInput)) {
                versionInputError.setText(positiveNumberErrorMSG);
                return;
            } else {
                versionInputError.setText("");
            }

            String orderNumberInput = orderNumberField.getText();
            if (!InputValidation.areNumbers(versionInput) || !InputValidation.fieldIsNotEmpty(orderNumberInput)) {
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

            // Creating the module
            this.logic.newModule(dropdown.getValue(), title, version,
                    Integer.valueOf(orderNumberField.getText()), descriptionField.getText(),
                    contactField.getText(), contactEmailField.getText(), 0);
            moduleSuccessfullyCreatedView();
        });
        this.gui.goToNext(view, "Create a module");
    }

    // Creating a view for the user to edit a module
    private void editModuleView(Module moduleToEdit) {
        GridPane view = generateFormGrid();

        // form label
        Label welcomeToFormLabel = new Label("Editing: '" + moduleToEdit.getTitle() + "'");
        welcomeToFormLabel.setId("title");
        welcomeToFormLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        view.add(welcomeToFormLabel, 0, 0, 2, 1);
        GridPane.setHalignment(welcomeToFormLabel, HPos.LEFT);
        GridPane.setMargin(welcomeToFormLabel, new Insets(20, 0, 20, 0));

        // Order number input
        Label orderNumberLabel = new Label("Order it has within a course:");
        TextField orderNumberField = new TextField("" + moduleToEdit.getPositionWithinCourse());
        Text orderNumberInputError = new Text("Has to be a (positive) whole number");
        orderNumberInputError.setVisible(false);
        orderNumberInputError.setFill(Color.FIREBRICK);
        view.add(orderNumberLabel, 0, 1);
        view.add(orderNumberField, 1, 1);
        view.add(orderNumberInputError, 2, 1);

        // Checking (live) if the user doesn't put anything in other than an int
        orderNumberField.textProperty().addListener((change, oldValue, newValue) -> {
            if (!InputValidation.areNumbers(newValue) && InputValidation.fieldIsNotEmpty(newValue)) {
                orderNumberInputError.setVisible(true);
            } else {
                orderNumberInputError.setVisible(false);
            }
        });

        // Contactperson name input
        Label contactLabel = new Label("Name of contact:");
        TextField contactField = new TextField(moduleToEdit.getContactName());
        view.add(contactLabel, 0, 2);
        view.add(contactField, 1, 2);

        // Contactperson email input
        Label contactEmailLabel = new Label("Email of contact:");
        TextField contactEmailField = new TextField(moduleToEdit.getEmailAddress());
        view.add(contactEmailLabel, 0, 3);
        view.add(contactEmailField, 1, 3);

        // Status value
        Label statusLabel = new Label("Module status:");
        ComboBox<Status> dropdown = new ComboBox<>();
        dropdown.setValue(moduleToEdit.getStatus());
        dropdown.getItems().add(Status.ACTIVE);
        dropdown.getItems().add(Status.ARCHIVED);
        dropdown.getItems().add(Status.CONCEPT);
        view.add(statusLabel, 0, 5);
        view.add(dropdown, 1, 5);

        // Description
        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField(moduleToEdit.getDescription());
        view.add(descriptionLabel, 0, 4);
        view.add(descriptionField, 1, 4);

        Button editBtn = new Button("Edit module");
        editBtn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        view.add(editBtn, 0, 6);
        // Edit button

        Text nullOrAlreadyExistsErrorField = new Text("One or more fields are not filled!");
        nullOrAlreadyExistsErrorField.setVisible(false);
        nullOrAlreadyExistsErrorField.setFill(Color.FIREBRICK);
        view.add(nullOrAlreadyExistsErrorField, 1, 9);

        editBtn.setOnMouseClicked((clicked -> {

            if (hasNoInput(descriptionField) || hasNoInput(contactField)
                    || hasNoInput(contactEmailField)) {
                nullOrAlreadyExistsErrorField.setVisible(true);
                return;

            } else {
                nullOrAlreadyExistsErrorField.setVisible(false);

            }

            // Finally, editing the module by retrieving the state of all input fields when
            // the user pressed the edit button
            this.logic.editModule(contactEmailField.getText(), contactField.getText(), descriptionField.getText(),
                    dropdown.getValue(), Integer.parseInt(orderNumberField.getText()), moduleToEdit);

            moduleSuccessfullyEditedView();

        }));
        this.gui.goToNext(view, "Edit module");
    }

    // When a module is edited, this method creates a view to give the user a
    // general menu
    private void moduleSuccessfullyEditedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully Edited!");
        label.setId("title");
        Button homeBtn = new Button("Go home");
        Button editAnotherModuleBtn = new Button("Edit other");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(editAnotherModuleBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        editAnotherModuleBtn.setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).createView());
        this.gui.goToNext(view, "Successfully edited!");
    }

    // If a module can be created, a user has an easy option to directly add
    // another
    private void moduleSuccessfullyCreatedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully created!");
        label.setId("title");
        Button homeBtn = new Button("Go home");
        Button createAnotherModuleBtn = new Button("Create other");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(createAnotherModuleBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        createAnotherModuleBtn
                .setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).addModuleView());
        this.gui.goToNext(view, "Successfully created!");
    }

    // When a module is deleted, this method creates a view to give the user the
    // option to directly go back to module management again
    private void successfullyDeletedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully deleted!");
        label.setId("title");
        Button homeBtn = new Button("Go home");
        Button backBtn = new Button("Go back");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(backBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        backBtn.setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).createView());
        this.gui.goToNext(view, "Successfully deleted");
    }
}