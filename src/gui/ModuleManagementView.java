package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.ControlLogic;
import domain.Module;
import domain.Status;

//Class responsible for all the views that are related to the management of a module
public class ModuleManagementView extends View {
    private ControlLogic logic;

    public ModuleManagementView(GUI baseUI) {
        super(baseUI);
        this.logic = new ControlLogic();
    }

    // The following method creates the first view that the user sees when going
    // into module management.
    @Override
    public void createView() {
        // Initial layout setup
        GridPane view = generateGrid();

        // UI components
        Label createLabel = new Label("Create a module");
        Button addModuleBtn = new Button("+");
        addModuleBtn.setOnMouseClicked(clicked -> addModuleView());

        Label selectLabel = new Label("Select an existing module");
        ComboBox<String> dropdown = new ComboBox<>();
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");

        // porting the modules to the dropdown
        dropdown.setValue("-no module selected-");
        dropdown.getItems().add("-no module selected-");
        for (int i = 0; i < this.logic.getModules().size(); i++) {
            Module module = this.logic.getModules().get(i);
            dropdown.getItems().add(i + ": " + module.getTitle() + " (version: " + module.getVersion() + ")");
        }

        // button actions related to existing modules
        deleteBtn.setOnMouseClicked(clicked -> {
            if (dropdown.getValue().equals("-no module selected-")) {
                view.add(new Label("No module selected!"), 0, 4);
            } else {
                // getting the module to delete
                String[] splitted = dropdown.getValue().split(":");
                int indexToDelete = Integer.parseInt(splitted[0]);
                Module moduleToDelete = this.logic.getModules().get(indexToDelete);

                // called method deletes module + sends a true if succesfull
                if (this.logic.deleteModule(moduleToDelete)) {
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

    // Creating a view for the user to create a new module
    public void addModuleView() {
        // Initial layout setup
        GridPane view = addModuleFormGrid();

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
        versionInputError.setFill(Color.FIREBRICK);
        view.add(versionLabel, 0, 2);
        view.add(versionField, 1, 2);
        view.add(versionInputError, 2, 2);

        // Checking (live) if the user doesn't put anything in other than an int
        versionField.textProperty().addListener((change, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") && !(newValue.equals(""))) {
                versionInputError.setText("has to be a (positive) number!");
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
                orderNumberInputError.setText("has to be a (positive) number!");
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
        Label Status = new Label("Module status:");
        ComboBox<String> dropdown = new ComboBox<String>();
        Text noStatusSelectedError = new Text("");
        noStatusSelectedError.setFill(Color.FIREBRICK);
        dropdown.setValue("-please select a value-");
        dropdown.getItems().add("-please select a value-");
        dropdown.getItems().add("ACTIVE");
        dropdown.getItems().add("CONCEPT");
        dropdown.getItems().add("ARCHIVED");
        view.add(Status, 0, 5);
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
        Text nullOrAlreadyExistsField = new Text("");
        nullOrAlreadyExistsField.setFill(Color.FIREBRICK);
        view.add(nullOrAlreadyExistsField, 1, 9);

        // Input validation when user presses 'create'
        createButton.setOnMouseClicked((event) -> {
            // check if any field has no input
            if (hasNoInput(moduleNameField) || hasNoInput(descriptionField) || hasNoInput(contactField)
                    || hasNoInput(contactEmailField)) {
                nullOrAlreadyExistsField.setText("One or more fields are not filled!");
                return;
            } else {
                nullOrAlreadyExistsField.setText("");

            }

            // checking if dropdown has no selected value
            if (dropdown.getValue().equals("-please select a value-")) {
                noStatusSelectedError.setText("No value selected!");
                return;
            } else {
                noStatusSelectedError.setText("");

            }

            // Checking if version and order ar an int
            String versionInput = versionField.getText();
            if (!versionInput.matches("\\d+") && !versionField.equals("")) {
                versionInputError.setText("has to be a (positive) number!");
                return;
            } else {
                versionInputError.setText("");
            }

            String orderNumberInput = orderNumberField.getText();
            if (!orderNumberInput.matches("\\d+") && !orderNumberField.equals("")) {
                orderNumberInputError.setText("has to be a (positive) number!");
                return;

            } else {
                orderNumberInputError.setText("");
            }

            // If the whole input validation is done, the module gets created if it doesn't
            // exists yet.
            String title = moduleNameField.getText();
            int version = Integer.valueOf(versionField.getText());
            if (this.logic.moduleAlreadyExistsBasedOn(title, version)) {
                nullOrAlreadyExistsField.setText("Module already exists!");
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
                    contactField.getText(), contactEmailField.getText());
            moduleSuccessfullyCreatedView();

        });

        // Futher layout setup
        activate(view, "Create module");

    }

    // Method responsible for the layout setup of the add module view
    public GridPane addModuleFormGrid() {
        GridPane grid = new GridPane();

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col1.setPercentWidth(17);
        col2.setPercentWidth(50);
        col3.setPercentWidth(18);

        grid.getColumnConstraints().addAll(col1, col2, col3);

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25));
        grid.setVgap(20);
        grid.setHgap(30);

        return grid;

    }

    // If a module can be created, an user has an easy option to directly add
    // another
    private void moduleSuccessfullyCreatedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully created!");
        Button homeBtn = new Button("Go home");
        Button anotherModuleBtn = new Button("Make another module");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(anotherModuleBtn, 2, 1);

        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        anotherModuleBtn
                .setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).moduleSuccessfullyCreatedView());
        activate(view, "Successfully created!");
    }

    // Method to help checking if input is empty
    private boolean hasNoInput(TextField moduleNameField) {
        if (moduleNameField.getText().trim().equals("")) {
            return true;
        }
        return false;

    }
}