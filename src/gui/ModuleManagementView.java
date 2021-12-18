package gui;

import java.util.List;

import javafx.geometry.Insets;
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
    }

    @Override
    public void createView() {
        // Initial layout setup
        GridPane view = new GridPane();
        view.setAlignment(Pos.CENTER);
        view.setHgap(40);
        view.setVgap(10);

        // UI components
        // Component to go the create module view
        Label createLabel = new Label("Create a module");
        Button createBtn = new Button("+");
        createBtn.setOnMouseClicked(clicked -> createModuleView());
        // components to select modules and edit or delete them
        Label selectLabel = new Label("Select an existing module");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");

        ComboBox<String> dropdown = new ComboBox<>();
        for (Module module : this.logic.getModules()) {
            dropdown.getItems().add(module.getTitle() + " (Version " + module.getVersion() + ")");
        }

        // futher layout setup
        view.add(createLabel, 1, 0);
        view.add(createBtn, 1, 1);
        view.add(selectLabel, 0, 0);
        view.add(dropdown, 0, 1);
        view.add(editBtn, 0, 2);
        view.add(deleteBtn, 0, 3);
        activate(view, "Module management");

    }

    public void createModuleView() {
        // This is a module form
        // Initial layout setup

        VBox view = new VBox();
        view.setPadding(new Insets(30));
        // text fields

        Label nameModule = new Label("Titel:");
        TextField moduleField = new TextField();
        //
        //
        Label version = new Label("Version:");
        TextField versionField = new TextField();

        versionField.textProperty().addListener((change, oldValue, newValue) -> {
            // NewValue gebruiken
            if (!(checkTextIsNumber(newValue)) && !(newValue.equals(""))) {
                version.setText("Version (has to be a number!):");
            } else {
                version.setText("Version:");
            }

        });

        Label trackingNumber = new Label("Trackingnumber:");
        TextField trckField = new TextField();
        /// Test if text is an number

        // Apart

        Label description = new Label("Description:");
        TextField descriptionField = new TextField();

        Label contact = new Label("Contactnaam:");
        TextField contactField = new TextField();

        Label contactEmail = new Label("ContactEmail");
        TextField contactEmField = new TextField();
        // Buttons
        Button create = new Button("Create module");
        create.setOnMouseClicked((event) -> {

            // Version and trackingNumber convert to int
            if (!(checkTextIsNumber(versionField.getText()))) {
                Label warning = new Label("Version is not a number");
                view.getChildren().add(warning);
            } else {
                // Command create

            }

        });
        // Futher layout setup
        view.getChildren().addAll(nameModule, moduleField, version, versionField, trackingNumber,
                trckField,
                description,
                descriptionField,
                contact, contactField, contactEmail, contactEmField, create);

        activate(view, "Create module");
    }

    // Edit modules

    public static boolean checkTextIsNumber(String text) {
        if (text == null) {
            return true;
        }
        try {
            int number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // TODO: handle exception
            return false;
        }

        return true;
    }
}
