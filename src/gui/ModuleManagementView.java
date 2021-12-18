package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logic.ControlLogic;
import domain.Module;

public class ModuleManagementView extends View {
    private ControlLogic logic;

    public ModuleManagementView(GUI baseUI) {
        super(baseUI);
        this.logic = new ControlLogic();

        // Temporary test!
    }

    @Override
    public void createView() {
        // Initial layout setup
        GridPane view = generateGrid();

        // UI components
        // Component to go the create module view
        Label createLabel = new Label("Create a module");
        Button createBtn = new Button("+");
        createBtn.setOnMouseClicked(clicked -> addModuleView());
        // components to select modules and edit or delete them
        Label selectLabel = new Label("Select an existing module");
        ComboBox<String> dropdown = new ComboBox<>();

        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");

        // porting the modules to the dropdown
        dropdown.setValue("-no module selected-");
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

                successfullyDeletedView();

            }

        });

        // futher layout setup
        view.add(createLabel, 1, 0);
        view.add(createBtn, 1, 1);
        view.add(selectLabel, 0, 0);
        view.add(dropdown, 0, 1);
        view.add(editBtn, 0, 2);
        view.add(deleteBtn, 0, 3);

        activate(view, "Module management");

    }

    private void successfullyDeletedView() {
        GridPane view = generateGrid();
        Label label = new Label("Successfully deleted!");
        Button homeBtn = new Button("Go home");
        Button backBtn = new Button("Go back");

        view.add(label, 1, 0);
        view.add(homeBtn, 0, 1);
        view.add(backBtn, 2, 1);

        // button action events
        homeBtn.setOnMouseClicked(clicked -> new HomeView(this.gui).createView());
        backBtn.setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).createView());
        activate(view, "Successfully deleted");
    }

    private GridPane generateGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(10);

        return grid;
    }

    public void addModuleView() {
        // This is a module form
        // Initial layout setup
        VBox view = new VBox();

        // text fields
        Label nameModule = new Label("Titel:");
        TextField moduleField = new TextField();

        Label version = new Label("Version:");
        TextField versionField = new TextField();

        Label trackingNumber = new Label("Trackingnumber:");
        TextField trckField = new TextField();

        Label description = new Label("Description:");
        TextField descriptionField = new TextField();

        Label contact = new Label("Contactnaam:");
        TextField contactField = new TextField();

        Label contactEmail = new Label("ContactEmail");
        TextField contactEmField = new TextField();
        // Buttons
        Button create = new Button("Create module");
        create.setOnMouseClicked((event) -> {

        });
        // Futher layout setup
        view.getChildren().addAll(nameModule, moduleField, version, versionField, trackingNumber, trckField,
                description,
                descriptionField,
                contact, contactField, contactEmail, contactEmField, create);
        activate(view, "Create module");
    }

}
